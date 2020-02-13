package de.stea1th.web.service;

import de.stea1th.commonlibrary.dto.PersonDto;

import java.security.Principal;

public interface PersonService {

    PersonDto get(int id);

    PersonDto getByPrincipal(Principal principal);

}
