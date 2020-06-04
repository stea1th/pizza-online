package de.stea1th.orderproductservice.repository;

import de.stea1th.orderproductservice.entity.OrderProductCost;
import de.stea1th.orderproductservice.entity.OrderProductCostPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
public interface OrderProductCostRepository extends JpaRepository<OrderProductCost, OrderProductCostPK> {

    @Query("SELECT opc.quantity FROM OrderProductCost opc WHERE opc.id = :orderProductCostId ")
    Integer findQuantityById(@Param("orderProductCostId") OrderProductCostPK id);

    @Query("SELECT SUM(opc.quantity) FROM OrderProductCost opc WHERE opc.id.orderId = :orderId ")
    Integer findSumQuantitiesByOrderId(@Param("orderId") int orderId);

    @Query("SELECT opc FROM OrderProductCost opc WHERE opc.id.orderId = :orderId ")
    List<OrderProductCost> findAllByOrderId(@Param("orderId") int orderId);

    @Query("SELECT opc.id.productCostId FROM OrderProductCost opc WHERE opc.id.orderId = :orderId ")
    List<Integer> findAllProductCostIdsByOrderId(@Param("orderId") int orderId);

}
