package de.stea1th.persist.service;


import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import de.stea1th.persist.entity.ProductCost;

import java.util.List;

public interface ProductCostService {

    List<ProductCostInCartDto> getAllProductCostsByKeycloak(String keycloak);
}
