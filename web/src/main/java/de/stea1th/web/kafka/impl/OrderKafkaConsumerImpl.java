package de.stea1th.web.kafka.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.dto.CompletedOrderDto;
import de.stea1th.commonslibrary.dto.OrderDto;
import de.stea1th.web.kafka.OrderKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderKafkaConsumerImpl implements OrderKafkaConsumer {

    private OrderDto orderDto;
    private List<Integer> years;
    private List<CompletedOrderDto> completedOrderDtoList;

    private ObjectMapper objectMapper = new ObjectMapper();

    public OrderDto getOrderDto() {
        var temp = orderDto;
        orderDto = null;
        return temp;
    }

    public List<Integer> getYears() {
        var temp = years;
        years = null;
        return temp;
    }

    public List<CompletedOrderDto> getCompletedOrderDtoList() {
        var temp = completedOrderDtoList;
        completedOrderDtoList = null;
        return temp;
    }

    @KafkaListener(topics = "${order.receive.complete}", groupId = "pizza-online")
    public void processReceiveCompleteTime(String message) {
        log.info("received message = {}", message);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            orderDto = objectMapper.readValue(message, OrderDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${order.receive.complete.year}", groupId = "pizza-online")
    public void processReceiveCompletedYears(String message) {
        log.info("received message = {}", message);
        try {
            years = objectMapper.readValue(message, new TypeReference<List<Integer>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics="${order.receive.completed.orders}", groupId = "pizza-online")
    public void processReceiveCompletedOrders(String message) {
        log.info("received message = {}", message);
        try {
            completedOrderDtoList = objectMapper.readValue(message, new TypeReference<List<CompletedOrderDto>>() {});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
