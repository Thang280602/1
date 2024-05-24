package com.shop.shoes.service;


import com.shop.shoes.dto.ImageProductDTO;

import java.util.List;


public interface ImageProductSevice {
    ImageProductDTO create(ImageProductDTO imageProductDTO);

    List<ImageProductDTO> findByProductDetail(Long id);

    void deleteByProductDetailId(Long id);
}
