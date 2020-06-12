package de.stea1th.orderservice.service;

import de.stea1th.orderservice.dto.TimeIntervalDto;
import de.stea1th.orderservice.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Order getUncompletedOrderByPersonKeycloak(String keycloak);

    List<TimeIntervalDto> getInterval(String keycloak);

    List<Order> getOrdersByTimeValue(String keycloak, String value);

//    Order getUncompletedOrderByPerson(Person person);

//    Order completeOrder(String keycloak);
//
//    List<Integer> getCompletedYearsByPerson(String keycloak);
//
//    List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto);

    Order complete(String keycloak);

}
