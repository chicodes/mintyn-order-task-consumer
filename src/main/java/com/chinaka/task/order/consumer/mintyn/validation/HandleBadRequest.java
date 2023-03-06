package com.chinaka.task.order.consumer.mintyn.validation;

import com.chinaka.task.order.consumer.mintyn.dto.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class HandleBadRequest {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<OrderResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(new OrderResponse("500", "Something went wrong", errors, null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExistException.class)
    public ResponseEntity<OrderResponse> orderExistException(OrderExistException exception) {
        log.error("Order already exist Exception {}", exception.getMessage());
        return new ResponseEntity<>(new OrderResponse("404", exception.getMessage(), null, null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<OrderResponse> handleGenericException(final Exception ex, final WebRequest request) {
        if (ex instanceof NumberFormatException) {
            System.out.println("handleGenericException ....");
            return new ResponseEntity<>(new OrderResponse("500", ex.getMessage(), null, null), HttpStatus.BAD_REQUEST);
        }
        System.out.println("handleGenericException ....");
        return new ResponseEntity<>(new OrderResponse("500", "Failed with Invalid data" +ex.getLocalizedMessage(), null, null), HttpStatus.BAD_REQUEST);
    }

}