package com.chinaka.task.order.consumer.mintyn.validation;


import lombok.Data;

@Data
public class OrderExistException extends Exception {
    private final String message;
    private final String code;
    public OrderExistException(String message, String code) {
       this.message = message;
       this.code = code;
    }
}
