package de.stea1th.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.persist.entity.Person;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@Getter
public class KafkaConsumerImpl implements KafkaConsumer {

    private final PersonService personService;

    private KafkaProducer kafkaProducer;

    private Person person;

    @Autowired
    public KafkaConsumerImpl(PersonService personService, KafkaProducer kafkaProducer) {
        this.personService = personService;
        this.kafkaProducer = kafkaProducer;
        person = new Person();
    }

    @KafkaListener(topics = "pizza-online.kafka.get.id", groupId = "pizza-online")
    public void processGetPerson(String id) {
        log.info("received id = {}", id);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Integer personId = objectMapper.readValue(id, Integer.class);
            person = personService.get(personId);
            kafkaProducer.produce("pizza-online.kafka.get.person", "pizza-online", person);
            log.info("Person data: {} sent to topic {}", objectMapper.writeValueAsString(person), "pizza-online.kafka.get.person");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
