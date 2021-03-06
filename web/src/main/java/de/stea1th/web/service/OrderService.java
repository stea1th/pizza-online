package de.stea1th.web.service;

import de.stea1th.commonslibrary.dto.CompletedOrderDto;
import de.stea1th.commonslibrary.dto.CompletedOrdersRequestDto;
import de.stea1th.commonslibrary.dto.LocalDateTimeDto;
import de.stea1th.commonslibrary.dto.TimeIntervalDto;

import java.util.List;

public interface OrderService {

    LocalDateTimeDto complete(String keycloak);

    List<TimeIntervalDto> getInterval(String keycloak);

    List<CompletedOrderDto> getCompletedOrders(CompletedOrdersRequestDto completedOrdersRequestDto);
}
