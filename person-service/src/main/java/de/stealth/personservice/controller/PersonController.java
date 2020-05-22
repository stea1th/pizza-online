package de.stealth.personservice.controller;

import de.stea1th.commonslibrary.dto.PersonDto;
import de.stealth.personservice.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
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
        return personDto == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @GetMapping(value = "/details")
    public ResponseEntity<PersonDto> getDetails(Principal principal) {
        log.info("get person with keycloak: {}", principal.getName());
        PersonDto personDto = personService.getByPrincipal(principal);
        log.info("received person with keycloak: {} {}", principal.getName(), personDto == null ? "not exists" : personDto);
        return personDto == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(personDto, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity save(@RequestBody PersonDto personDto) {
        personService.save(personDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
