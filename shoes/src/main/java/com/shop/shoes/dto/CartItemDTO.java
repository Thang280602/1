package com.shop.shoes.dto;

import com.shop.shoes.model.Cart;
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
public class CartItemDTO {

    private Long id;

    private int quantity;

    private Cart cart;

    private ProductDetail productDetail;
}
