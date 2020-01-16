package de.stea1th.persist;

import de.stea1th.persist.service.PersonService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PersistApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(PersistApplication.class, args);
        PersonService personService = context.getBean(PersonService.class);
        personService.get(1000);
    }

}
