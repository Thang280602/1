package com.shop.shoes.service;

import com.shop.shoes.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order create(Order Order);
    Order findById(Long id);
}
