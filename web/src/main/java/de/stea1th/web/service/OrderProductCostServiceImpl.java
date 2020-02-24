package de.stea1th.web.service;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import de.stea1th.web.kafka.OrderProductCostKafkaConsumer;
import de.stea1th.web.kafka.ProductCostKafkaConsumer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public OrderProductCostServiceImpl(KafkaProducer kafkaProducer, OrderProductCostKafkaConsumer orderProductCostKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.orderProductCostKafkaConsumer = orderProductCostKafkaConsumer;
    }

    @Override
    public int addToCart(OrderProductCostDto orderProductCostDto) {
        return getIntFromPersist(orderProductCostDto, productOrderAddToCartTopic);
    }


    @Override
    public int getQuantitiesSumInCart(String keycloak) {
        return getIntFromPersist(keycloak, productOrderGetSum);
    }

    @SneakyThrows
    private <T> int getIntFromPersist(T object, String topic) {
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
