package com.shop.shoes.util;

import com.shop.shoes.dto.OrderDetailDTO;
import com.shop.shoes.model.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDetailUtils {
    private final ModelMapper modelMapper;

    public OrderDetail mapOrderDetailDTOtoOrderDetail(OrderDetailDTO orderDetailDTO) {
        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }

    public OrderDetailDTO mapOrderDetailtoOrderDetailDTO(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }
}
