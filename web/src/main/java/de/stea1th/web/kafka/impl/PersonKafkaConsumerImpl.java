package de.stea1th.web.kafka.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.dto.PersonDto;
import de.stea1th.web.kafka.PersonKafkaConsumer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonKafkaConsumerImpl implements PersonKafkaConsumer {

    private PersonDto personDto;

    @SneakyThrows
    @KafkaListener(topics = "${person.receive.person}", groupId = "pizza-online")
    public void processGetPerson(String person) {
        log.info("received person = {}", person);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            personDto = objectMapper.readValue(person, PersonDto.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public PersonDto getPersonDto() {
        PersonDto tempPersonDto = this.personDto;
        this.personDto = null;
        return tempPersonDto;
    }
}
