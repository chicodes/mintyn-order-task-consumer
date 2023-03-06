package com.chinaka.task.order.consumer.mintyn.model;

import com.chinaka.task.order.consumer.mintyn.util.ProductStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price ;

    private String currencyCode;

    private  int quantity;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    private Date dateCreated;
    private Date dateUpdated;
}
