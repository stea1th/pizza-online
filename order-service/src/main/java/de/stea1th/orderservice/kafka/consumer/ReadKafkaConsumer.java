package de.stea1th.orderservice.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.service.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReadKafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OrderService orderService;

    public ReadKafkaConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @SneakyThrows
    @KafkaListener(topics = "${order.get}")
    @SendTo
    public String getOrderByKeycloak(String message) {
        log.info("receiving keycloak: {}", message);
        Order order = orderService.getUncompletedOrderByPersonKeycloak(message);
        return objectMapper.writeValueAsString(order);
    }
}
