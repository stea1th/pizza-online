package de.stea1th.web.service;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.OrderProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProductServiceImpl implements OrderProductService {

    private final KafkaProducer kafkaProducer;

    @Value("${order-product.cart.add}")
    private String productOrderAddToCartTopic;

    public OrderProductServiceImpl(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void addToCart(OrderProductDto orderProductDto) {
        log.info("sending with kafka: {}", orderProductDto);
        kafkaProducer.produce(productOrderAddToCartTopic, "pizza-online", orderProductDto);
    }
}
