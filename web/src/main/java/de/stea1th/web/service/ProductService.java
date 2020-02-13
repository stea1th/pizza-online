package de.stea1th.web.service;


import de.stea1th.commonlibrary.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto get(int productId);

    List<ProductDto> getAllProductsForKeycloak(String keycloak);

}
