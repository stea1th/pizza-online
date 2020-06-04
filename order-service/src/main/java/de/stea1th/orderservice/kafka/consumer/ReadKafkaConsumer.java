package de.stea1th.orderservice.kafka.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.orderservice.entity.Order;
import de.stea1th.orderservice.service.OrderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    @SneakyThrows
    @KafkaListener(topics = "${order.get.all.time-interval}")
    @SendTo
    public String getOrdersByTimeValue(String message) {
        log.info("receiving message: {}", message);
        Map<String, String> mes = objectMapper.readValue(message, new TypeReference<Map<String, String>>() {
        });
        String keycloak = null;
        String value = null;
        for(Map.Entry<String, String> entry : mes.entrySet()) {
            keycloak = entry.getKey();
            value = entry.getValue();
        }
        return objectMapper.writeValueAsString(orderService.getOrdersByTimeValue(keycloak, value));


    }
}
