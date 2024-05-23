package com.shop.shoes.util;

import com.shop.shoes.dto.CartDTO;
import com.shop.shoes.model.Cart;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartUtils {
    private final ModelMapper modelMapper;

    public Cart mapCartDTOtoCart(CartDTO cartDTO) {
        return modelMapper.map(cartDTO, Cart.class);
    }

    public CartDTO mapCarttoCartDTO(Cart cart) {
        return modelMapper.map(cart, CartDTO.class);
    }
}
