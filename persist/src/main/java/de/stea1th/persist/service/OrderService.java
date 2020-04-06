package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.PdfCreatorDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

    Order getUncompletedOrderByPerson(Person person);

    Order completeOrder(String keycloak);

    List<PdfCreatorDto> getCompletedOrdersForTimeIntervalByPersonKeycloak(String keycloak, LocalDateTime interval);

    List<Integer> getCompletedYearsByPerson(String keycloak);

}
