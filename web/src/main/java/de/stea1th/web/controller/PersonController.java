package de.stea1th.web.controller;

import de.stea1th.kafkalibrary.dto.PersonDto;
import de.stea1th.web.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
        return personDto == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(personDto, HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/details")
    public ResponseEntity<PersonDto> getDetails(Principal principal) {
        log.info("get person with keycloak: {}", principal.getName());
        PersonDto personDto = personService.getByPrincipal(principal);
        log.info("received person with keycloak: {} {}", principal.getName(), personDto == null ? "not exists" : personDto);
        return personDto == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(personDto, HttpStatus.ACCEPTED);
    }
}
