package de.stea1th.persist.repository;

import de.stea1th.persist.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Integer> {

    default List<Product> getAll() {
        return this.findAll();
    }

    default Product get(int id) throws ClassNotFoundException {
        return this.findById(id).orElseThrow(() -> new ClassNotFoundException("no such product exists with id:" + id));
    }
}
