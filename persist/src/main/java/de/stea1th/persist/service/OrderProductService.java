package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductDto;
import de.stea1th.persist.entity.OrderProduct;
import de.stea1th.persist.entity.OrderProductPK;

public interface OrderProductService {

    OrderProduct save(OrderProductDto orderProductDto);

    OrderProduct delete(OrderProductPK orderProductId);

}
