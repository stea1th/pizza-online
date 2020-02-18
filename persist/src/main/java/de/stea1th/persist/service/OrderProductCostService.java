package de.stea1th.persist.service;

import de.stea1th.commonslibrary.dto.OrderProductDto;
import de.stea1th.persist.entity.OrderProductCost;
import de.stea1th.persist.entity.OrderProductCostPK;

public interface OrderProductCostService {

    OrderProductCost save(OrderProductDto orderProductDto);

    OrderProductCost delete(OrderProductCostPK orderProductId);

}
