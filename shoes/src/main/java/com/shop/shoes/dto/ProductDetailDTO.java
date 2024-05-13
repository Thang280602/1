package com.shop.shoes.dto;

import com.shop.shoes.model.Color;
import com.shop.shoes.model.ImageProduct;
import com.shop.shoes.model.Product;
import com.shop.shoes.model.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailDTO {
    private Long id;

    private String image;

    private Double price;

    private Boolean status;

    private int quantity;

    private Double discount;

    private Long productID;

    private Long colorID;

    private Long sizeID;

}
