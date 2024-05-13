package com.shop.shoes.service;

import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Product findById(Long id);
    Product createProduct(ProductDTO productDTO);
    Product updateProduct(Long id,ProductDTO productDTO);

    void deleteByProductId(Long id);
}
