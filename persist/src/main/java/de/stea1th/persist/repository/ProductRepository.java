package de.stea1th.persist.repository;

import de.stea1th.commonslibrary.exception.MyEntityNotFoundException;
import de.stea1th.persist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Integer> {

    default Product get(int id) throws MyEntityNotFoundException {
        return this.findById(id).orElseThrow(() -> new MyEntityNotFoundException("no such product with id: + " + id + " exists"));
    }

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN ProductCost pc ON p = pc.product " +
            "LEFT JOIN OrderProductCost opc ON pc = opc.productCost " +
            "LEFT JOIN Order o ON o = opc.order WHERE o.id = :orderId ")
    List<Product> getAllByOrderId(@Param("orderId") int orderId);


    @Query("SELECT p FROM Product p WHERE p.frozen = FALSE ")
    List<Product> getAllWithoutFrozen();
}
