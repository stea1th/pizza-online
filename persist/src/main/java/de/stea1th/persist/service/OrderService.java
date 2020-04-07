package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.CompletedOrderDto;
import de.stea1th.commonslibrary.dto.CompletedOrdersRequestDto;
import de.stea1th.persist.entity.Order;
import de.stea1th.persist.entity.Person;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

    Order getUncompletedOrderByPerson(Person person);

    Order completeOrder(String keycloak);

    List<Integer> getCompletedYearsByPerson(String keycloak);

    List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto);

}
