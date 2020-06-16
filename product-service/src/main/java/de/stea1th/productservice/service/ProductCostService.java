package de.stea1th.productservice.service;


import de.stea1th.productservice.dto.ProductCostDto;
import de.stea1th.productservice.entity.ProductCost;

import java.util.List;

public interface ProductCostService {

    ProductCostDto get(int productCostId);

    List<ProductCostDto> getAll();

    List<ProductCostDto> getAllByIds(List<Integer> ids);

}
