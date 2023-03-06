package com.chinaka.task.order.consumer.mintyn.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
//@Table(name = "orders", indexes = @Index(name = "id_index", columnList = "id"))
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private Date dateCreated;
    private Date dateUpdated;
}
