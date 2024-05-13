package com.shop.shoes.service.Impl;


import com.shop.shoes.dto.ImageProductDTO;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.exception.domain.ImageProductException;
import com.shop.shoes.model.ImageProduct;
import com.shop.shoes.repository.ImageProductRepository;
import com.shop.shoes.service.ImageProductSevice;
import com.shop.shoes.util.ImageProductUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImageProductSeviceImpl implements ImageProductSevice {
	@Autowired
	public final ImageProductRepository imageProductRepository;
	@Autowired
	public final ImageProductUtils imageProductUtils;

    public ImageProductSeviceImpl(ImageProductRepository imageProductRepository, ImageProductUtils imageProductUtils) {
        this.imageProductRepository = imageProductRepository;
        this.imageProductUtils = imageProductUtils;
    }

    @Override
	public ImageProductDTO create(ImageProductDTO imageProductDTO) {
		ImageProduct imageProduct = imageProductUtils.mapImageProductDTOtoImageProduct(imageProductDTO);
		try {
			ImageProduct imageProductSave = imageProductRepository.save(imageProduct);
			return imageProductUtils.mapImageProducttoImageProductDTO(imageProductSave);
		}catch (Exception e){
			throw new ImageProductException("Lỗi khi thêm ảnh");
		}
	}
	@Override
	public void deleteByProductDetailId(Long id) {
		try {
			this.imageProductRepository.deleteByProductDetailId(id);
		}catch(Exception e){
			throw new CategoryNotFoundException("Xóa ảnh không thành công");
		}
	}
	
}
