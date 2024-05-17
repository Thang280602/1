package com.shop.shoes.service.Impl;

import com.shop.shoes.model.Cart;
import com.shop.shoes.model.CartItem;
import com.shop.shoes.repository.CartItemRepository;
import com.shop.shoes.service.CartItemService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem checkCardItem(Long cardId, Long idProductDetail) {
        return this.cartItemRepository.findByCartIdAndProductDetailId(cardId, idProductDetail);
    }

    @Override
    public CartItem create(CartItem cartItem) {
        return this.cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findCartItemByCart(Cart cart) {
        return this.cartItemRepository.findCartItemByCart(cart);
    }

    @Override
    public void deleteCartItemById(Long id) {
        this.cartItemRepository.deleteById(id);
    }

}
