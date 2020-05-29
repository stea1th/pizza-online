package de.stea1th.productservice.service;


import de.stea1th.productservice.entity.ProductCost;

import java.util.List;

public interface ProductCostService {

    ProductCost get(int productCostId);

    List<ProductCost> getAll();

}
