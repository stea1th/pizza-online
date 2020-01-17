package de.stea1th.web.service;

import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.kafkalibrary.dto.PersonDto;
import de.stea1th.web.kafka.PersonKafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final KafkaProducer kafkaProducer;

    private final PersonKafkaConsumer personKafkaConsumer;

    @Autowired
    public PersonServiceImpl(KafkaProducer kafkaProducer, PersonKafkaConsumer personKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.personKafkaConsumer = personKafkaConsumer;
    }

    @Override
    public PersonDto get(int id) {
        kafkaProducer.produce("pizza-online.kafka.get.id", "pizza-online", id);
        PersonDto personDto = null;
        while (personDto == null) {
            personDto = personKafkaConsumer.getPersonDto();
        }
        return personDto;
    }
}
