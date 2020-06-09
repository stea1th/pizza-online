package de.stealth.personservice.controller;

import de.stealth.personservice.entity.Person;
import de.stealth.personservice.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/api/")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> get(@PathVariable("id") int id) {
        log.info("Get person with id: {}", id);
        Person person = personService.get(id);
        log.info("Received person with id: {} {}", id, person == null ? "not exists" : person);
        return person == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping(value = "/details")
//    public ResponseEntity<Person> getDetails(Principal principal) {
    public ResponseEntity<Person> getDetails(@AuthenticationPrincipal User user) {
//        log.info("Get person with keycloak: {}", principal.getName());
        log.info("Get person with keycloak: {}", user.getUsername());
//        String keycloak = principal.getName();
        String keycloak = user.getUsername();
        Person person = personService.getByKeycloak(keycloak);
        log.info("Received person with keycloak: {} {}", user.getUsername(), person == null ? "not exists" : person);
        return person == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST) : new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<HttpStatus> save(@RequestBody Person person) {
        log.info("Saving person: {}", person);
        personService.save(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
