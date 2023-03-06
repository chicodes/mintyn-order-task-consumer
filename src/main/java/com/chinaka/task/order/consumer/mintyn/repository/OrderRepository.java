package com.chinaka.task.order.consumer.mintyn.repository;

import com.chinaka.task.order.consumer.mintyn.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //Page<Order> findAll(Pageable pageable);
    Order findOrderById(Long id);

    @Query(value = "SELECT * FROM orders WHERE date_created BETWEEN :startDate AND  :endDate ", nativeQuery = true)
    Page<Order> findOrderByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

}
