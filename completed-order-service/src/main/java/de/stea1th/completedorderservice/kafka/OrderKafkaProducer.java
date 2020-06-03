package de.stea1th.completedorderservice.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderKafkaProducer extends KafkaProducer {

    @Value("${order.get}")
    private String getOrderTopic;

    public OrderKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    @SneakyThrows
    public int getOrderIdByKeycloak(String keycloak) {
        String json = super.produce(getOrderTopic, keycloak);
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode idNode = rootNode.get("id");
        return idNode.asInt();
    }

    public String getOrderAsJson(String keycloak) {
        return super.produce(getOrderTopic, keycloak);
    }
}
