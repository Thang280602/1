package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.CategoryConstant;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.Cart;
import com.shop.shoes.model.CartItem;
import com.shop.shoes.repository.CartItemRepository;
import com.shop.shoes.service.CartItemService;
import com.shop.shoes.util.CartItemUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartItemUtils cartItemUtils;

    @Override
    public CartItem checkCardItem(Long cardId, Long idProductDetail) {
        return cartItemRepository.findByCartIdAndProductDetailId(cardId, idProductDetail);

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

    @Override
    public void deleteCartItemByCartId(Long cartId) {
        this.cartItemRepository.deleteByCartId(cartId);
    }

    @Override
    public CartItem findById(Long id) {
        return this.cartItemRepository.findById(id)
                .orElseThrow(() -> new ExceptionMessage("Không tìm thấy CartItem có id trên"));
    }

    @Override
    public CartItem update(CartItem cartItem, Long id) {
        if (id != cartItem.getId()) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_NOT_FOUND);
        }
        try {
            return cartItemRepository.save(cartItem);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_ALREADY_EXITS);
        }
    }

}
