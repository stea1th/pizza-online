package de.stea1th.web.service;

import de.stea1th.commonslibrary.dto.LocalDateTimeDto;

public interface OrderService {

    LocalDateTimeDto complete(String keycloak);
}
