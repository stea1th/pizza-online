package de.stea1th.web.kafka;

import de.stea1th.kafkalibrary.dto.ProductDto;

import java.util.List;

public interface ProductKafkaConsumer {

    List<ProductDto> getProductDtoList();

}
