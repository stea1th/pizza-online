package de.stea1th.persist.service;

import de.stea1th.persist.entity.OrderProduct;
import de.stea1th.persist.entity.OrderProductPK;

public interface OrderProductService {

    OrderProduct save(OrderProduct orderProduct);

    OrderProduct delete(OrderProductPK orderProductId);

}
