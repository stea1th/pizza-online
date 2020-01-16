package de.stea1th.persist.service;

import de.stea1th.persist.entity.Person;
import de.stea1th.persist.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public Person get(int personId) {
        Person one = personRepository.getOne(personId);
        log.info(one.getFirstName());
        return one;
    }
}
