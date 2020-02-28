package de.stea1th.persist.kafka;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderKafkaConsumer {

    private KafkaProducer kafkaProducer;
    private OrderService orderService;

    @Value("${order.receive.complete}")
    private String orderReceiveCompleteTopic;

    public OrderKafkaConsumer(KafkaProducer kafkaProducer, OrderService orderService) {
        this.kafkaProducer = kafkaProducer;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${order.get.complete}", groupId = "pizza-online")
    public void processGetComplete(String keycloak) {
        log.info("received keycloak = {}", keycloak);
        Order order = orderService.completeOrder(keycloak);
        log.info("LocalDateTime sending: {}", order);
        kafkaProducer.produce(orderReceiveCompleteTopic, order);
    }
}
