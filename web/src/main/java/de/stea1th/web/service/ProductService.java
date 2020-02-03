package de.stea1th.web.service;


import de.stea1th.kafkalibrary.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAll();

    ProductDto get(int productId);

}
