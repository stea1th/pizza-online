package de.stea1th.persist.repository;

import de.stea1th.persist.entity.OrderProduct;
import de.stea1th.persist.entity.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {

}
