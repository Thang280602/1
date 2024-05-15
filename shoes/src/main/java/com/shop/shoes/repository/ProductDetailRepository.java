package com.shop.shoes.repository;

import com.shop.shoes.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {
    @Query("select p from ProductDetail p where p.product.id = ?1")
    List<ProductDetail> findProductDetailByProductId(Long id);
}
