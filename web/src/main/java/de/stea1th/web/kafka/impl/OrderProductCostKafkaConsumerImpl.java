package de.stea1th.web.kafka.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.web.kafka.OrderProductCostKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class OrderProductCostKafkaConsumerImpl implements OrderProductCostKafkaConsumer {


    private Integer countProductsInCart;

    public Integer getCountProductsInCart() {
        Integer temp = countProductsInCart;
        countProductsInCart = null;
        return temp;
    }

    @KafkaListener(topics = "${order-product-cost.receive.sum}", groupId = "pizza-online")
    public void processReceiveSumQuantities(String message) {
        log.info("received sum of products in cart = {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            countProductsInCart = objectMapper.readValue(message, Integer.class);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
