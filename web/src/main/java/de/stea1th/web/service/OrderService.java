package de.stea1th.web.service;

import de.stea1th.commonslibrary.dto.LocalDateTimeDto;
import de.stea1th.commonslibrary.dto.OrderDto;

public interface OrderService {

    LocalDateTimeDto complete(String keycloak);


}
