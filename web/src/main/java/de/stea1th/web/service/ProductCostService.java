package de.stea1th.web.service;

import de.stea1th.commonslibrary.dto.ProductCostInCartDto;

import java.util.List;

public interface ProductCostService {

    List<ProductCostInCartDto> getProductCostsInCart(String keycloak);
}
