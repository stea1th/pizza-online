package de.stea1th.persist.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.CompletedOrdersRequestDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderKafkaConsumer {

    private KafkaProducer kafkaProducer;
    private OrderService orderService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${order.receive.complete}")
    private String orderReceiveCompleteTopic;

    @Value("${order.receive.complete.year}")
    private String orderReceiveCompletedYears;

    @Value("${order.receive.completed.orders}")
    private String orderReceiveCompletedOrders;

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

    @KafkaListener(topics = "${order.get.complete.year}", groupId = "pizza-online")
    public void processGetCompletedYears(String keycloak) {
        log.info("received keycloak = {}", keycloak);
        var years = orderService.getCompletedYearsByPerson(keycloak);
        log.info("Completed years sending: {}", years);
        kafkaProducer.produce(orderReceiveCompletedYears, years);
    }

    @KafkaListener(topics = "${order.get.completed.orders}", groupId = "pizza-online")
    public void processGetCompletedOrders(String message) {
        log.info("received message = {}", message);
        try {
            var completedOrdersRequest = objectMapper.readValue(message, CompletedOrdersRequestDto.class);
            var completedOrders = orderService.getCompletedOrders(completedOrdersRequest);
            log.info("!!!!!!!!!!!!!!!! {}", completedOrders.isEmpty());
            log.info("Completed orders sending: {}", completedOrders);
            kafkaProducer.produce(orderReceiveCompletedOrders, completedOrders);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
