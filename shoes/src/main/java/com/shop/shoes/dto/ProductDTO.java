package com.shop.shoes.dto;


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
public class ProductDTO  {
    private Long id;

    private String image;

    private Double price;


    private String productName;

    private String productAddress;

    private String description;

    private Boolean productStatus;

    private Long categoryID;

    public ProductDTO(String image, Double price, String productName, String productAddress, String description, Boolean productStatus, Long categoryID) {
        this.image = image;
        this.price = price;
        this.productName = productName;
        this.productAddress = productAddress;
        this.description = description;
        this.productStatus = productStatus;
        this.categoryID = categoryID;
    }
}
