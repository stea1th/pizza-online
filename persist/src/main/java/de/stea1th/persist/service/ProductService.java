package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll(boolean withFrozen);

    List<ProductDto> getAllProductsByKeycloak(String keycloak);
}
