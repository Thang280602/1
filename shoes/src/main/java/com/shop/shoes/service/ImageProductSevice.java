package com.shop.shoes.service;


import com.shop.shoes.dto.ImageProductDTO;
import com.shop.shoes.model.ImageProduct;
import com.shop.shoes.model.ProductDetail;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ImageProductSevice {
	ImageProductDTO create(ImageProductDTO imageProductDTO);
	List<ImageProductDTO> findByProductDetail(Long id);
	void deleteByProductDetailId(Long id);
}
