package com.chinaka.task.order.consumer.mintyn.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String busStop;
    private Date dateCreated;
    private Date dateUpdated;
}
