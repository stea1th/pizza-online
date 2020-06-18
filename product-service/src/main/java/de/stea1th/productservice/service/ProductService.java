package de.stea1th.productservice.service;


import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll(boolean withFrozen);

    ProductDto get(int id);
}
