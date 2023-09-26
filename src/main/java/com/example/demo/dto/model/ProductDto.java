package com.example.demo.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotBlank(message = "name product is required")
    private String name;
    @NotNull(message = "price product is required")
    private float price;
    @NotNull(message = "remainQuantity product is required")
    private int remainQuantity;
    @NotNull(message = "soldQuantity product is required")
    private int soldQuantity;
    @NotBlank(message = "describe product is required")
    private String describe;
    private String image;
}
