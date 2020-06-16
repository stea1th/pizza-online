package de.stea1th.productservice.service;


import de.stea1th.productservice.dto.ProductDto;
import de.stea1th.productservice.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll(boolean withFrozen);

    Product get(int id);

    Product attachPic(Product product);

    ProductDto transformToDto(Product product);
}
