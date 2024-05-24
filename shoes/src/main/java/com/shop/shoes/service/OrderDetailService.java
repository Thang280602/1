package com.shop.shoes.service;

import com.shop.shoes.model.Order;
import com.shop.shoes.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail add(OrderDetail orderDetail);

    List<OrderDetail> getByOrderId(Order order);
}
