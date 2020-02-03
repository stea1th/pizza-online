package de.stea1th.persist.service;

import de.stea1th.persist.entity.Product;
import de.stea1th.persist.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product get(int productId) {
        Product product = null;
        try {
            product = productRepository.get(productId);
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
        return product;
    }
}
