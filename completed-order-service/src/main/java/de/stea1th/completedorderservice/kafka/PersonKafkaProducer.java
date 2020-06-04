package de.stea1th.completedorderservice.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.completedorderservice.dto.PersonDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonKafkaProducer extends KafkaProducer {

    @Value("${person.get}")
    private String getPersonTopic;

    public PersonKafkaProducer(ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate, ObjectMapper objectMapper) {
        super(replyingKafkaTemplate, objectMapper);
    }

    @SneakyThrows
    public PersonDto getPersonDtoByKeycloak(String keycloak) {
        String json = produce(getPersonTopic, keycloak);
        return objectMapper.readValue(json, PersonDto.class);
    }
}
