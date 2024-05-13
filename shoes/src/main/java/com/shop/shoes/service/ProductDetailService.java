package com.shop.shoes.service;

import com.shop.shoes.dto.ProductDetailDTO;
import com.shop.shoes.model.ProductDetail;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> getAll();
    ProductDetail findById(Long id);
    ProductDetail createProductDetail(ProductDetailDTO productDetailDTO);
    ProductDetail updateProductDetail(Long id, ProductDetailDTO productDetailDTO);

    void deleteByProductId(Long id);
}
