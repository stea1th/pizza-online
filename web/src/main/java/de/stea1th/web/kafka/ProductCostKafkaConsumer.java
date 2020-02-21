package de.stea1th.web.kafka;

import de.stea1th.commonslibrary.dto.ProductCostInCartDto;

import java.util.List;

public interface ProductCostKafkaConsumer {

    List<ProductCostInCartDto> getProductCostInCartDtoList();
}
