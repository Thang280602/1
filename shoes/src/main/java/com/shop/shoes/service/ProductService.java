package com.shop.shoes.service;

import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product findById(Long id);

    Product createProduct(ProductDTO productDTO, String fileName);

    Product updateProduct(Long id, ProductDTO productDTO, String fileName);

    List<Product> getFirst8Products();

    void deleteByProductId(Long id);
}
