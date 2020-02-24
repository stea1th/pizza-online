package de.stea1th.web.service;

import de.stea1th.commonslibrary.dto.OrderProductCostDto;

public interface OrderProductCostService {

    int addToCart(OrderProductCostDto orderProductCostDto);

    int getQuantitiesSumInCart(String keycloak);

    int updateInCart(OrderProductCostDto orderProductCostDto);

}
