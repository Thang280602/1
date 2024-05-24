package com.shop.shoes.service;

import com.shop.shoes.dto.CartDTO;
import com.shop.shoes.model.Cart;
import com.shop.shoes.model.User;

public interface CartService {
    Long checkCartByUser(Long idUser);

    Cart findCartByUser(User user);

    Cart createCart(CartDTO cartDTO);

}
