package de.stea1th.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.persist.entity.Person;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@Getter
public class KafkaConsumerImpl implements KafkaConsumer {

    private final PersonService personService;

    private Person person;

    public KafkaConsumerImpl(PersonService personService) {
        this.personService = personService;
        person = new Person();
    }

    @KafkaListener(topics = "pizza-online.kafka.get.person", groupId = "pizza-online")
    public void processGetPerson(String id) {
        log.info("received id = {}", id);
        ObjectMapper objectMapper = new ObjectMapper();
//        int personId = Integer.parseInt(id);


        try {
            Integer personId = objectMapper.readValue(id, Integer.class);
            person = personService.get(personId);
            log.info("Person data: {}", objectMapper.writeValueAsString(person));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
