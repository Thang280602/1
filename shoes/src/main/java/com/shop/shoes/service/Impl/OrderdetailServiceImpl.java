package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.CategoryConstant;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.model.Order;
import com.shop.shoes.model.OrderDetail;
import com.shop.shoes.repository.OrderDetailRepository;
import com.shop.shoes.service.OrderDetailService;
import com.shop.shoes.util.OrderDetailUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderdetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailUtils orderDetailUtils;

    @Override
    public OrderDetail add(OrderDetail orderDetail) {
        if (orderDetail.getId() != null) {
            throw new IllegalArgumentException("Cannot create a category with a predefined ID.");
        }
        try {
            return orderDetailRepository.save(orderDetail);

        } catch (DataIntegrityViolationException e) {
            throw new CategoryNotFoundException(CategoryConstant.CATEGORY_ALREADY_EXITS);
        }
    }

    @Override
    public List<OrderDetail> getByOrderId(Order order) {
        return this.orderDetailRepository.findByOrder(order);
    }
}
