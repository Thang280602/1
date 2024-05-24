package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.CategoryConstant;
import com.shop.shoes.dto.CartDTO;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.model.Cart;
import com.shop.shoes.model.Category;
import com.shop.shoes.model.User;
import com.shop.shoes.repository.CartRepository;
import com.shop.shoes.service.CartService;
import com.shop.shoes.util.CartUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    public CartRepository cartRepository;
    @Autowired
    public CartUtils cartUtils;

    @Override
    public Long checkCartByUser(Long idUser) {
        return this.cartRepository.countId(idUser);
    }

    @Override
    public Cart findCartByUser(User user) {
        return this.cartRepository.findByUser(user).get(0);
    }

    @Override
    public Cart createCart(CartDTO cartDTO) {
        try {
            Cart cart = cartUtils.mapCartDTOtoCart(cartDTO);
            return this.cartRepository.save(cart);
        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_ALREADY_EXITS);
        }
    }


}
