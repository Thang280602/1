package com.shop.shoes.util;

import com.shop.shoes.dto.CartItemDTO;
import com.shop.shoes.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemUtils {
    private final ModelMapper modelMapper;

    public CartItem mapCartItemDTOtoCartItem(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    public CartItemDTO mapCartItemtoCartItemDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }
}
