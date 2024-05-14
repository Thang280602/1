package com.shop.shoes.repository;

import com.shop.shoes.model.Product;
import com.shop.shoes.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findTop8ByOrderByIdAsc();
}
