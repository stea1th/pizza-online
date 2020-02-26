package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.persist.entity.Product;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

//    ProductDto get(int productId);

    List<ProductDto> getAllProductsByKeycloak(String keycloak);
}
