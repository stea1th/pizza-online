package de.stea1th.orderservice.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class OrderProductKafkaProducer extends KafkaProducer {

    @Value("${order-product.all}")
    private String getAllOrderProductTopic;

    public OrderProductKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    public String getAllOrderProductAsJson(int orderId) {
        return produce(getAllOrderProductTopic, orderId);
    }

    @SneakyThrows
    public boolean isOrderProductExists(int orderId) {
        String json = getAllOrderProductAsJson(orderId);
        List list = objectMapper.readValue(json, List.class);
        return list.size() > 0;
    }





}
