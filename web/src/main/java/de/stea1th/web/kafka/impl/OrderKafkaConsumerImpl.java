package de.stea1th.web.kafka.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.dto.OrderDto;
import de.stea1th.web.kafka.OrderKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class OrderKafkaConsumerImpl implements OrderKafkaConsumer {

    private LocalDateTime localDateTime;

    private OrderDto orderDto;

    public OrderDto getOrderDto() {
        OrderDto temp = orderDto;
        orderDto = null;
        return temp;
    }

    public LocalDateTime getLocalDateTime() {
        LocalDateTime temp = localDateTime;
        localDateTime = null;
        return temp;
    }

    @KafkaListener(topics = "${order.receive.complete}", groupId = "pizza-online")
    public void processReceiveCompleteTime(String message) {
        log.info("received message = {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            orderDto = objectMapper.readValue(message, OrderDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
