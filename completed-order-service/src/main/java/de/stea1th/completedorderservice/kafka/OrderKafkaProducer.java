package de.stea1th.completedorderservice.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class OrderKafkaProducer extends KafkaProducer {

    @Value("${order.get}")
    private String getOrderTopic;

    @Value("${order.get.all.time-interval}")
    private String getOrdersForTimeIntervalTopic;

    public OrderKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    @SneakyThrows
    public int getOrderIdByKeycloak(String keycloak) {
        logMessage(keycloak, getOrderTopic);
        String json = super.produce(getOrderTopic, keycloak);
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode idNode = rootNode.get("id");
        return idNode.asInt();
    }

    public String getOrderAsJson(String keycloak) {
        logMessage(keycloak, getOrderTopic);
        return super.produce(getOrderTopic, keycloak);
    }

    public String getOrdersForTimeInterval(Map<String, String> message) {
        logMessage(message, getOrdersForTimeIntervalTopic);
        return super.produce(getOrdersForTimeIntervalTopic, message);
    }

    private void logMessage(Object message, String topic) {
        log.info("Sending message {} to topic {}", message, topic);
    }
}
