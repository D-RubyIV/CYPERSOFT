package com.example.demo.api;


import com.example.demo.dto.model.ProductDto;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/product")
public class ProductApiController {
    private final ProductService productService;
    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/get")
    public ResponseEntity<?> get(){
        return new ResponseEntity<>(productService.selectAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Validated @RequestBody ProductDto dto){
        return new ResponseEntity<>(productService.add(dto), HttpStatus.OK);
    }

    @PostMapping("/delete/{idProduct}")
    public ResponseEntity<?> delete(@PathVariable UUID idProduct){
        return new ResponseEntity<>(productService.delete(idProduct).getMap(), HttpStatus.OK);
    }

}
