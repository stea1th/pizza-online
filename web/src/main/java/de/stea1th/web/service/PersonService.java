package de.stea1th.web.service;

import de.stea1th.commonslibrary.dto.PersonDto;

import java.security.Principal;

public interface PersonService {

    PersonDto get(int id);

    PersonDto getByPrincipal(Principal principal);

    void save(PersonDto personDto);

}
