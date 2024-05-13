package com.shop.shoes.service;


import com.shop.shoes.dto.ImageProductDTO;
import com.shop.shoes.model.ImageProduct;
import org.springframework.stereotype.Service;


public interface ImageProductSevice {
	ImageProductDTO create(ImageProductDTO imageProductDTO);
	void deleteByProductDetailId(Long id);
}
