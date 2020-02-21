package de.stea1th.persist.service;


import de.stea1th.persist.entity.ProductCost;

import java.util.List;

public interface ProductCostService {

    List<ProductCost> getAllProductCostsByKeycloak(String keycloak);
}
