package de.stea1th.persist.kafka;


import de.stea1th.persist.service.OrderProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProductKafkaConsumer {

    private final OrderProductService orderProductService;


    public OrderProductKafkaConsumer(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }


    @KafkaListener(topics = "${order-product.save.id}", groupId = "pizza-online")
    public void processSaveOrderProduct(String message) {

    }
}
