package de.stea1th.productservice.service;

import de.stea1th.productservice.converter.ProductCostConverter;
import de.stea1th.productservice.dto.ProductCostDto;
import de.stea1th.productservice.entity.ProductCost;
import de.stea1th.productservice.repository.ProductCostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ProductCostServiceImpl implements ProductCostService {

    private final ProductCostRepository productCostRepository;
    private final ProductCostConverter productConverter;

    public ProductCostServiceImpl(ProductCostRepository productCostRepository, ProductCostConverter productConverter) {
        this.productCostRepository = productCostRepository;
        this.productConverter = productConverter;
    }

    @Transactional
    @Override
    public ProductCostDto get(int productCostId) {
        log.info("Getting product cost with id: {}", productCostId);
        return productConverter.transformToDto(productCostRepository.findById(productCostId).orElse(null), true);
    }

    @Override
    public List<ProductCostDto> getAll() {
        log.info("Getting all product costs");
        return productConverter.transformToDtoList(productCostRepository.findAll(), true);
    }

    @Override
    public List<ProductCostDto> getAllByIds(List<Integer> ids) {
        log.info("Getting all product costs by ids: {}", ids);
        List<ProductCost> list = productCostRepository.findAllByIds(ids);
        return productConverter.transformToDtoList(list, true);
    }

}
