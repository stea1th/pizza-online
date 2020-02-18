package de.stea1th.persist.repository;

import de.stea1th.persist.entity.OrderProductCost;
import de.stea1th.persist.entity.OrderProductCostPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface OrderProductCostRepository extends JpaRepository<OrderProductCost, OrderProductCostPK> {

}
