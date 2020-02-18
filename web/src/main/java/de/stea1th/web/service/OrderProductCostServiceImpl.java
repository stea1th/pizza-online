package de.stea1th.web.service;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.OrderProductCostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProductCostServiceImpl implements OrderProductCostService {

    private final KafkaProducer kafkaProducer;

    @Value("${order-product.cart.add}")
    private String productOrderAddToCartTopic;

    public OrderProductCostServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void addToCart(OrderProductCostDto orderProductCostDto) {
        log.info("sending with kafka: {}", orderProductCostDto);
        kafkaProducer.produce(productOrderAddToCartTopic, "pizza-online", orderProductCostDto);
    }
}
