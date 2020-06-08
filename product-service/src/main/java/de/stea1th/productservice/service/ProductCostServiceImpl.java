package de.stea1th.productservice.service;

import de.stea1th.productservice.dto.ProductCostDto;
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
    public ProductCostDto get(int productCostId) {
        log.info("Get product cost with id: {}", productCostId);
        return transformToDto(productCostRepository.findById(productCostId).orElse(null));
    }

    @Override
    public List<ProductCostDto> getAll() {
        log.info("Get all product costs");
        return transformToDtoList(productCostRepository.findAll());
    }

    @Override
    public List<ProductCostDto> getAllByIds(List<Integer> ids) {
        log.info("Get all product costs by ids: {}", ids);
        List<ProductCost> list = productCostRepository.findAllByIds(ids);
        return transformToDtoList(list);
    }

    private ProductCostDto transformToDto(ProductCost productCost) {
        if(productCost == null) return null;
        ProductCostDto productCostDto = new ProductCostDto();
        productCostDto.setId(productCost.getId());
        productCostDto.setPrice(productCost.getPrice());
        productCostDto.setDiscount(productCost.getDiscount());
        productCostDto.setFrozen(productCost.isFrozen());
        productCostDto.setProduct(productService.attachPic(productCost.getProduct()));
        return productCostDto;
    }

    private List<ProductCostDto> transformToDtoList(List<ProductCost> list) {
        return list.stream().map(this::transformToDto).collect(Collectors.toList());
    }
}
