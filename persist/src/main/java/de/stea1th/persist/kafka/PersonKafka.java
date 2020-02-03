package de.stea1th.persist.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.persist.entity.Person;
import de.stea1th.persist.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonKafka {

    private KafkaProducer kafkaProducer;

    private PersonService personService;

    private Person person;

    @Autowired
    public PersonKafka(KafkaProducer kafkaProducer, PersonService personService) {
        this.kafkaProducer = kafkaProducer;
        this.personService = personService;
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
            log.error(e.getMessage());
        }
    }
}
