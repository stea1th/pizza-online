package de.stea1th.web.kafka;

import de.stea1th.commonlibrary.dto.ProductDto;

import java.util.List;

public interface ProductKafkaConsumer {

    List<ProductDto> getProductDtoList();

}
