package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_Product")
public class ProductEntity extends BaseModel{
    private String name;
    private float price;
    private int remainQuantity;
    private int soldQuantity;
    private String describe;
    private String image;
}
