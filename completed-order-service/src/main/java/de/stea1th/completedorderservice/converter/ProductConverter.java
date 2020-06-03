package de.stea1th.completedorderservice.converter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductConverter {

    private final ProductCostConverter productCostConverter;

    public ProductConverter(ProductCostConverter productCostConverter) {
        this.productCostConverter = productCostConverter;
    }

//    private final ImageConverter imageConverter;
//
//    public ProductConverter(@Lazy ProductCostConverter productCostConverter, ImageConverter imageConverter) {
//        this.productCostConverter = productCostConverter;
//        this.imageConverter = imageConverter;
//    }
//
//    public ProductDto convertToDtoWithoutProductCostList(Product product) {
//        var productDto = new ProductDto();
//        productDto.setId(product.getId());
//        productDto.setName(product.getName());
//        productDto.setDescription(product.getDescription());
//        productDto.setFrozen(product.isFrozen());
//        try {
//            String base64 = imageConverter.encodeFileFromResourcesToBase64(product.getPicture());
//            productDto.setPicture(base64);
//        } catch (IOException e) {
//            productDto.setPicture(product.getPicture());
//        }
//        return productDto;
//    }
//
//    public ProductDto convertToDto(Product product, boolean withFrozen) {
//        var productDto = convertToDtoWithoutProductCostList(product);
//        productDto.setProductCostList(productCostConverter.convertListToDto(product.getProductCostList(), withFrozen));
//        return productDto;
//    }
//
//    public List<ProductDto> convertListToDto(List<Product> products, boolean withFrozen) {
//        return products
//                .stream()
//                .map(el-> this.convertToDto(el, withFrozen))
//                .collect(Collectors.toList());
//    }

}
