package de.stea1th.web.controller;

import de.stea1th.kafkalibrary.dto.PersonDto;
import de.stea1th.web.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/person")
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonDto> get(@PathVariable("id") int id) {
        log.info("get person with id: {}", id);
        PersonDto personDto = personService.get(id);
        log.info("received person with id: {} {}", id, personDto == null ? "not exists" : personDto);
        return personDto == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(personDto, HttpStatus.ACCEPTED);
    }

}
