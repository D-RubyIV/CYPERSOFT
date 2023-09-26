package com.example.demo.service;

import com.example.demo.dto.model.ProductDto;
import com.example.demo.dto.response.MapResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.ProductEntity;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public ProductEntity add(ProductDto dto){
        if (productRepository.findByName(dto.getName()) != null){
            throw new CustomException.BadRequestException("Name Product is already exits");
        }
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(dto, productEntity);
        return productRepository.save(productEntity);
    }
    public MapResponseDto delete(UUID id){
        productRepository.findById(id).orElseThrow(() -> new CustomException.BadRequestException("Product is not exits"));
        productRepository.deleteById(id);
        Map<String, String> map = new HashMap<>();
        map.put("message","Product has been deleted");
        return new MapResponseDto(map);
    }
}
