package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.CategoryConstant;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.model.Order;
import com.shop.shoes.model.User;
import com.shop.shoes.repository.OrderRepository;
import com.shop.shoes.service.OrderService;
import com.shop.shoes.util.OrderUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderUtils orderUtils;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Order create(Order order) {
        if (order.getId() != null) {
            throw new IllegalArgumentException("Cannot create a category with a predefined ID.");
        }
        try {
            return orderRepository.save(order);

        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_ALREADY_EXITS);
        }
    }

    @Override
    public Order update(Long id, Integer status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            return orderRepository.save(order);


        } else {
            throw new RuntimeException("Order không tồn tại với ID: " + id);
        }
    }

    @Override
    public Order updateStatus(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(4);
            return orderRepository.save(order);

        } else {
            return null;
        }

    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(CategoryConstant.CATEGORY_NOT_FOUND));

    }

    @Override
    public List<Order> finOrdersByStatus(Integer status, User user) {
        return this.orderRepository.findOrderByStatusAndUser(status, user);

    }
}
