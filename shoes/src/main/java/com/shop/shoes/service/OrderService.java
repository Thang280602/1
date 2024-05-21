package com.shop.shoes.service;

import com.shop.shoes.model.Order;
import com.shop.shoes.model.User;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    Order create(Order Order);
    Order updateStatus(Long id);
    Order findById(Long id);
    List<Order> finOrdersByStatus(Integer status, User user);
}
