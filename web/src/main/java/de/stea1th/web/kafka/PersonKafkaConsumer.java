package de.stea1th.web.kafka;

import de.stea1th.kafkalibrary.dto.PersonDto;

public interface PersonKafkaConsumer {

    void processGetPerson(String person);

    PersonDto getPersonDto();
}
