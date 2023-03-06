package com.chinaka.task.order.consumer.mintyn.repository;

import com.chinaka.task.order.consumer.mintyn.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //Page<Order> findAll(Pageable pageable);
    Product findProductById(Long id);
}
