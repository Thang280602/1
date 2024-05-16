package com.shop.shoes.service.Impl;

import com.shop.shoes.repository.CartRepository;
import com.shop.shoes.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    public CartRepository cartRepository;
    @Override
    public Long countId(Long idUser) {
        return this.cartRepository.countId(idUser);
    }
}
