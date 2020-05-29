package de.stea1th.productservice.service;



import de.stea1th.productservice.entity.ProductCost;
import de.stea1th.productservice.repository.ProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final ProductCostRepository productCostRepository;


    public ProductCostServiceImpl( ProductCostRepository productCostRepository) {
        this.productCostRepository = productCostRepository;
    }


    @Transactional
    @Override
    public ProductCost get(int productCostId) {
        return productCostRepository.findById(productCostId).get();
    }

    @Override
    public List<ProductCost> getAll() {
        return productCostRepository.findAll();
    }
}
