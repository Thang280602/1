package com.shop.shoes.dto;

import com.shop.shoes.model.Order;
import com.shop.shoes.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {

    private Long id;
    private Double price;
    private int quantity;

    private Order order;

    private ProductDetail productDetail;
}
