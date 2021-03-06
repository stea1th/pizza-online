package de.stea1th.web.service.impl;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.PersonDto;
import de.stea1th.web.kafka.PersonKafkaConsumer;
import de.stea1th.web.service.PersonService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    @Value("${person.get.keycloak}")
    private String personGetKeycloakTopic;

    @Value("${person.save.details}")
    private String personSaveTopic;

    @Autowired
    public PersonServiceImpl(KafkaProducer kafkaProducer, PersonKafkaConsumer personKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.personKafkaConsumer = personKafkaConsumer;
    }

    @Override
    public PersonDto get(int id) {
        kafkaProducer.produce(personGetIdTopic, "pizza-online", id);
        return getPersonDtoFromKafka();
    }

    @Override
    public PersonDto getByPrincipal(Principal principal) {

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        String keycloak = keycloakAuthenticationToken.getName();
        kafkaProducer.produce(personGetKeycloakTopic, "pizza-online", keycloak);
        PersonDto personDto = getPersonDtoFromKafka();
        if (personDto != null) {
            KeycloakSecurityContext securityContext = ((KeycloakPrincipal) keycloakAuthenticationToken.getPrincipal()).getKeycloakSecurityContext();
            AccessToken accessToken = securityContext.getToken();
            setPersonDetails(accessToken, personDto);
        }
        return personDto;
    }

    @Override
    public void save(PersonDto personDto) {
        kafkaProducer.produce(personSaveTopic, "pizza-online", personDto);
    }

    @SneakyThrows
    private PersonDto getPersonDtoFromKafka() {
        PersonDto personDto = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            personDto = personKafkaConsumer.getPersonDto();
            if (personDto != null) break;
        }
        return personDto;
    }

    private void setPersonDetails(AccessToken accessToken, PersonDto personDto) {
        boolean needSave = false;
        if (personDto.getFirstName() == null) {
            personDto.setFirstName(accessToken.getGivenName());
            needSave = true;
        }
        if (personDto.getLastName() == null) {
            personDto.setLastName(accessToken.getFamilyName());
            needSave = true;
        }
        if (personDto.getEmail() == null) {
            personDto.setEmail(accessToken.getEmail());
            needSave = true;
        }
        if (needSave) save(personDto);
    }
}
