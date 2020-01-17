package de.stea1th.web.controller;

import de.stea1th.web.service.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/api/person")
public class PersonController {


    private final KafkaProducer kafkaProducer;

    public PersonController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        log.info("Get person with id: {}", id);
        kafkaProducer.getById("pizza-online.kafka.get.person","pizza-online", id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
