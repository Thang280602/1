package com.shop.shoes.util;

import com.shop.shoes.dto.ImageProductDTO;
import com.shop.shoes.model.ImageProduct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageProductUtils {
    public final ModelMapper modelMapper;

    public ImageProduct mapImageProductDTOtoImageProduct(ImageProductDTO imageProductDTO) {
        return modelMapper.map(imageProductDTO, ImageProduct.class);
    }

    public ImageProductDTO mapImageProducttoImageProductDTO(ImageProduct imageProduct) {
        return modelMapper.map(imageProduct, ImageProductDTO.class);
    }
}
