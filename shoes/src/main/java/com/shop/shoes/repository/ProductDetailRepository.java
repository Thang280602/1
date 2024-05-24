package com.shop.shoes.repository;

import com.shop.shoes.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    @Query("select p from ProductDetail p where p.product.id = ?1")
    List<ProductDetail> findProductDetailByProductId(Long id);

    @Query("select p from ProductDetail p where p.product.id = :productId and p.color.colorName = :colorName and p.size.sizeName = :sizeName")
    ProductDetail findProductDetailByColorNameAndSizeName(@Param("productId") Long id, @Param("colorName") String colorName, @Param("sizeName") String sizeName);
}
