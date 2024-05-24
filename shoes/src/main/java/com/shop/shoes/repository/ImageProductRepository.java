package com.shop.shoes.repository;


import com.shop.shoes.model.ImageProduct;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Transactional
public interface ImageProductRepository extends JpaRepository<ImageProduct, Long> {
    @Modifying
    @Query("Delete from ImageProduct i Where i.productDetail.id=?1")
    void deleteByProductDetailId(Long id);

    @Query("select i from ImageProduct i Where i.productDetail.id=?1")
    List<ImageProduct> findImageProductByProductDetail(Long id);
}
