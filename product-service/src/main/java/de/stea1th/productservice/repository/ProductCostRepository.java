package de.stea1th.productservice.repository;

import de.stea1th.productservice.entity.ProductCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductCostRepository extends JpaRepository<ProductCost, Integer> {


    //    @Query("SELECT pc FROM ProductCost pc " +
//            "LEFT JOIN OrderProductCost opc ON pc = opc.productCost " +
//            "LEFT JOIN Order o ON o = opc.order WHERE o.id = :orderId " +
//            "ORDER BY pc.id DESC ")
//    List<ProductCost> getAllByOrderId(@Param("orderId") int orderId);

    @Query("SELECT pc FROM ProductCost pc WHERE pc.id in :ids ")
    List<ProductCost> findAllByIds(@Param("ids") List<Integer> ids);
}
