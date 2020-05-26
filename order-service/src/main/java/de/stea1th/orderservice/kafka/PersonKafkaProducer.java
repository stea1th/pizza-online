package de.stea1th.orderservice.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.orderservice.dto.PersonDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
@Slf4j
public class PersonKafkaProducer extends KafkaProducer {

    @Value("${person.get}")
    private String getPersonTopic;

    public PersonKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    @SneakyThrows
    public int getPersonIdByKeycloak(String keycloak) {
        String json = produce(getPersonTopic, keycloak);
        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode idNode = rootNode.get("id");
        return idNode.asInt();
    }


}
