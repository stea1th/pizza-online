package de.stea1th.persist.service;

import de.stea1th.persist.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product get(int productId);
}
