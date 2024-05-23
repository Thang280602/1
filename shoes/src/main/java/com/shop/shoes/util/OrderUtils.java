package com.shop.shoes.util;

import com.shop.shoes.dto.OrderDTO;
import com.shop.shoes.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderUtils {
    private final ModelMapper modelMapper;

    public Order mapOrderDTOtoOrder(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }

    public OrderDTO mapOrdertoOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}

