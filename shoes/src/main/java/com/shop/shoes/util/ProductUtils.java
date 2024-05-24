package com.shop.shoes.util;

import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUtils {
    private final ModelMapper modelMapper;

    public Product mapProductDtotoProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO mapProducttoProductDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
