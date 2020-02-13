package de.stea1th.persist.service;

import de.stea1th.persist.entity.OrderProduct;
import de.stea1th.persist.entity.OrderProductPK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderProductServiceImpl implements OrderProductService {


    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return null;
    }

    @Override
    public OrderProduct delete(OrderProductPK orderProductId) {
        return null;
    }
}
