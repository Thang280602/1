package com.shop.shoes.dto;

import com.shop.shoes.model.Category;
import com.shop.shoes.model.OrderDetail;
import com.shop.shoes.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;

    private String image;

    private  Double price;


    private String productName;

    private String productAddress;

    private String description;

    private Boolean productStatus;

    private Long categoryID;
}
