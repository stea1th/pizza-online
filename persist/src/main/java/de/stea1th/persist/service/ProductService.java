package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    List<ProductDto> getAllProductsByKeycloak(String keycloak);
}
