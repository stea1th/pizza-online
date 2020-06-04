package de.stea1th.productservice.service;

import de.stea1th.productservice.entity.ProductCost;
import de.stea1th.productservice.repository.ProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final ProductCostRepository productCostRepository;

    private final ProductService productService;


    public ProductCostServiceImpl(ProductCostRepository productCostRepository, ProductService productService) {
        this.productCostRepository = productCostRepository;
        this.productService = productService;
    }


    @Transactional
    @Override
    public ProductCost get(int productCostId) {
        log.info("Get product cost with id: {}", productCostId);
        return productCostRepository.findById(productCostId).orElse(null);
    }

    @Override
    public List<ProductCost> getAll() {
        log.info("Get all product costs");
        return productCostRepository.findAll();
    }

    public List<ProductCost> getAllByIds(List<Integer> ids) {
        log.info("Get all product costs by ids: {}", ids);
        List<ProductCost> list = productCostRepository.findAllByIds(ids);
        list.forEach(x -> x.setProduct(productService.attachPic(x.getProduct())));
        return list;
    }
}
