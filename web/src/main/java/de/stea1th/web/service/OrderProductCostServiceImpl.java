package de.stea1th.web.service;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.web.kafka.OrderProductCostKafkaConsumer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OrderProductCostServiceImpl implements OrderProductCostService {

    private final KafkaProducer kafkaProducer;

    private final OrderProductCostKafkaConsumer orderProductCostKafkaConsumer;

    @Value("#{new Integer('${kafka.service.attempt}')}")
    private Integer attempt;

    @Value("#{new Integer('${kafka.service.delay}')}")
    private Integer delay;

    @Value("${order-product-cost.cart.add}")
    private String productOrderAddToCartTopic;

    @Value("${order-product-cost.get.sum}")
    private String productOrderGetSum;

    @Value("${order-product-cost.cart.update}")
    private String productOrderUpdateInCartTopic;

    public OrderProductCostServiceImpl(KafkaProducer kafkaProducer, OrderProductCostKafkaConsumer orderProductCostKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.orderProductCostKafkaConsumer = orderProductCostKafkaConsumer;
    }

    @Override
    public int addToCart(OrderProductCostDto orderProductCostDto) {
        return getSumFromPersist(orderProductCostDto, productOrderAddToCartTopic);
    }


    @Override
    public int getQuantitiesSumInCart(String keycloak) {
        return getSumFromPersist(keycloak, productOrderGetSum);
    }

    @Override
    public int updateInCart(OrderProductCostDto orderProductCostDto) {
        return getSumFromPersist(orderProductCostDto, productOrderUpdateInCartTopic);
    }

    @SneakyThrows
    private <T> int getSumFromPersist(T object, String topic) {
        log.info("sending with kafka: {}", object);
        kafkaProducer.produce(topic, "pizza-online", object);
        Integer sum = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            sum = orderProductCostKafkaConsumer.getCountProductsInCart();
            if (sum != null) break;
        }
        return sum == null? 0 : sum;
    }
}
