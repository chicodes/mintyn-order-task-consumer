package com.chinaka.task.order.consumer.mintyn.dto;

import com.chinaka.task.order.consumer.mintyn.util.ProductStatus;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductDto {

    @NotNull(message="name not be empty" , groups = ProductDto.Validation.class)
    @NotBlank(message="name can not be empty" , groups = ProductDto.Validation.class)
    @NotEmpty(message="name can not be empty" , groups = ProductDto.Validation.class)
    private String name;

    @NotNull(message="description can not be empty" , groups = ProductDto.Validation.class)
    @NotBlank(message="description can not be empty" , groups = ProductDto.Validation.class)
    @NotEmpty(message="description can not be empty" , groups = ProductDto.Validation.class)
    private String description;

    @NotNull(message="imageLink can not be empty" , groups = ProductDto.Validation.class)
    @NotBlank(message="imageLink can not be empty" , groups = ProductDto.Validation.class)
    @NotEmpty(message="imageLink can not be empty" , groups = ProductDto.Validation.class)
    private String imageLink;

    @NotNull(message="price can not be empty" , groups = ProductDto.Validation.class)
    @NotBlank(message="price can not be empty" , groups = ProductDto.Validation.class)
    @NotEmpty(message="price can not be empty" , groups = ProductDto.Validation.class)
    private String price ;

    @NotNull(message="currencyCode can not be empty" , groups = ProductDto.Validation.class)
    @NotBlank(message="currencyCode can not be empty" , groups = ProductDto.Validation.class)
    @NotEmpty(message="currencyCode can not be empty" , groups = ProductDto.Validation.class)
    private String currencyCode;

    @NotNull(message="quantity can not be empty" , groups = ProductDto.Validation.class)
    @NotBlank(message="quantity can not be empty" , groups = ProductDto.Validation.class)
    @NotEmpty(message="quantity can not be empty" , groups = ProductDto.Validation.class)
    private  String quantity;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;




    public interface Validation{}
}
