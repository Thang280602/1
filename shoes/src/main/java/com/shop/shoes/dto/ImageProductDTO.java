package com.shop.shoes.dto;

import com.shop.shoes.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageProductDTO {
    private Long id;
    private String image;
    private ProductDetail productDetail;

}
