package com.shop.shoes.service;

import com.shop.shoes.model.Cart;
import com.shop.shoes.model.CartItem;

import java.util.List;

public interface CartItemService {
    CartItem checkCardItem(Long cardId, Long idProductDetail);
    CartItem create(CartItem cartItem);
    List<CartItem> findCartItemByCart(Cart cart);
    void deleteCartItemById(Long id);
    void deleteCartItemByCartId(Long cartId);
    CartItem findById(Long id);
    CartItem update(CartItem cartItem ,Long id);
}
