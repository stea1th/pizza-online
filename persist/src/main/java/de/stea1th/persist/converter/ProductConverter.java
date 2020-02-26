package de.stea1th.persist.converter;

import de.stea1th.commonslibrary.dto.ProductCostDto;
import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.persist.entity.Product;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    private final ProductCostConverter productCostConverter;

    public ProductConverter(@Lazy ProductCostConverter productCostConverter) {
        this.productCostConverter = productCostConverter;
    }

    public ProductDto convertToDtoWithoutProductCostList(Product product) {
        var productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPicture(product.getPicture());
        return productDto;
    }

    public ProductDto convertToDto(Product product) {
        var productDto = convertToDtoWithoutProductCostList(product);
        List<ProductCostDto> productCostDtoList = product.getProductCostList()
                .stream()
                .map(productCostConverter::convertToDto)
                .collect(Collectors.toList());
        productDto.setProductCostList(productCostDtoList);
        return productDto;
    }

}
