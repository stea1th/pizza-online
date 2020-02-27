package de.stea1th.persist.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.PersonDto;
import de.stea1th.persist.entity.Person;
import de.stea1th.persist.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonKafkaConsumer {

    private KafkaProducer kafkaProducer;

    private PersonService personService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private PersonDto person;

    @Value("${person.receive.person}")
    private String receivePersonTopic;

    @Autowired
    public PersonKafkaConsumer(KafkaProducer kafkaProducer, PersonService personService) {
        this.kafkaProducer = kafkaProducer;
        this.personService = personService;
    }

    @KafkaListener(topics = "${person.get.id}", groupId = "pizza-online")
    public void processGetPerson(String id) {
        log.info("received id = {}", id);
        try {
            Integer personId = objectMapper.readValue(id, Integer.class);
            person = personService.get(personId);
            if (person != null) {
                kafkaProducer.produce(receivePersonTopic, "pizza-online", person);
                log.info("person data: {} sent to topic {}", objectMapper.writeValueAsString(person), receivePersonTopic);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "${person.get.keycloak}", groupId = "pizza-online")
    public void processGetPersonByKeycloak(String keycloak) {
        log.info("received keycloak = {}", keycloak);
        try {
            person = personService.getDtoByKeycloak(keycloak);
            if (person != null) {
                kafkaProducer.produce(receivePersonTopic, "pizza-online", person);
                log.info("person data: {} sent to topic {}", objectMapper.writeValueAsString(person), receivePersonTopic);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics="${person.save.details}", groupId = "pizza-online")
    public void processSavePerson(String message) {
        log.info("received person = {}", message);
        try {
            Person person = objectMapper.readValue(message, Person.class);
            personService.save(person);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
