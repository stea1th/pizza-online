package de.stea1th.completedorderservice.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.completedorderservice.dto.CartElementDto;
import de.stea1th.completedorderservice.dto.PersonDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrderProductKafkaProducer extends KafkaProducer {

    @Value("${order-product.get.cart}")
    private String getCartByOrderIdTopic;

    public OrderProductKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    @SneakyThrows
    public List<CartElementDto> getCartElementsByOrderId(int orderId) {
        log.info("Sending orderId: {} to topic {}", orderId, getCartByOrderIdTopic);
        String json = produce(getCartByOrderIdTopic, orderId);
        return objectMapper.readValue(json, new TypeReference<List<CartElementDto>>() {
        });
    }


}
