package com.shop.shoes.util;

import com.shop.shoes.dto.ProductDetailDTO;
import com.shop.shoes.model.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductDetailUtils {
    private final ModelMapper modelMapper;
    public ProductDetailDTO mapProductDetailToProductDetailDTO(ProductDetail productDetail){
        return modelMapper.map(productDetail, ProductDetailDTO.class);
    }
    public ProductDetail mapProductDetailDTOtoProductDetail(ProductDetailDTO productDetailDTO){
        return modelMapper.map(productDetailDTO, ProductDetail.class);
    }
}
