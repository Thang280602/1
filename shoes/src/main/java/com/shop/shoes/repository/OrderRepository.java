package com.shop.shoes.repository;

import com.shop.shoes.model.Order;
import com.shop.shoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByStatusAndUser(Integer status, User user);
}
