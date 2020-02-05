package de.stea1th.web.service;

import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.kafkalibrary.dto.PersonDto;
import de.stea1th.web.kafka.PersonKafkaConsumer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final KafkaProducer kafkaProducer;

    private final PersonKafkaConsumer personKafkaConsumer;

    @Value("#{new Integer('${kafka.service.attempt}')}")
    private Integer attempt;

    @Value("#{new Integer('${kafka.service.delay}')}")
    private Integer delay;

    @Value("${person.get.id}")
    private String personGetIdTopic;

    @Autowired
    public PersonServiceImpl(KafkaProducer kafkaProducer, PersonKafkaConsumer personKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.personKafkaConsumer = personKafkaConsumer;
    }

    @SneakyThrows
    @Override
    public PersonDto get(int id) {
        kafkaProducer.produce(personGetIdTopic, "pizza-online", id);
        PersonDto personDto = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            personDto = personKafkaConsumer.getPersonDto();
            if (personDto != null) break;
        }
        return personDto;
    }
}
