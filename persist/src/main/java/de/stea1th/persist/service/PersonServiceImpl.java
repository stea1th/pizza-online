package de.stea1th.persist.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.persist.entity.Person;
import de.stea1th.persist.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    private KafkaProducer kafkaProducer;

    private Person person;

    public PersonServiceImpl(PersonRepository personRepository, KafkaProducer kafkaProducer) {
        this.personRepository = personRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    @Transactional
    public Person get(int personId) {
        Person person = null;
        try {
            person = personRepository.get(personId);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return person;
    }

    @KafkaListener(topics = "pizza-online.kafka.get.id", groupId = "pizza-online")
    public void processGetPerson(String id) {
        log.info("received id = {}", id);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Integer personId = objectMapper.readValue(id, Integer.class);
            person = get(personId);
            kafkaProducer.produce("pizza-online.kafka.get.person", "pizza-online", person);
            log.info("Person data: {} sent to topic {}", objectMapper.writeValueAsString(person), "pizza-online.kafka.get.person");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
