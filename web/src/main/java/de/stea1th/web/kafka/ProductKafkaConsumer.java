package de.stea1th.web.kafka;

import de.stea1th.commonslibrary.dto.ProductDto;

import java.util.List;

public interface ProductKafkaConsumer {

    List<ProductDto> getAllProducts();

    List<ProductDto> getProductsInCart();

}
