package com.shop.shoes.repository;

import com.shop.shoes.model.Cart;
import com.shop.shoes.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    CartItem findByCartIdAndProductDetailId(Long idCart,Long idProductDetail);
    void deleteByCartId(Long CartId);
    List<CartItem> findCartItemByCart(Cart cart);
}
