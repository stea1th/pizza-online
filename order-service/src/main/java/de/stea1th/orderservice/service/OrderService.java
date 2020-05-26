package de.stea1th.orderservice.service;

import de.stea1th.commonslibrary.dto.CompletedOrderDto;
import de.stea1th.commonslibrary.dto.CompletedOrdersRequestDto;
import de.stea1th.orderservice.entity.Order;

import java.util.List;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

//    Order getUncompletedOrderByPerson(Person person);

//    Order completeOrder(String keycloak);
//
//    List<Integer> getCompletedYearsByPerson(String keycloak);
//
//    List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto);

    Order complete(String keycloak);

}
