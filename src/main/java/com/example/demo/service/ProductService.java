package com.example.demo.service;

import com.example.demo.model.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    public List<ProductEntity> selectAll(){
        return productRepository.findAll();
    }

}
