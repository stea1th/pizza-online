package de.stea1th.web.service;


import de.stea1th.commonslibrary.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll(boolean withFrozen);

    ProductDto get(int productId);

    List<ProductDto> getProductsInCart(String keycloak);

}
