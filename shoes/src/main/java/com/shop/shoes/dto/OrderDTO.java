package com.shop.shoes.dto;

import com.shop.shoes.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String addressShip;
    private Date createAt;
    private String phone;
    private Integer status;
    private Double totalPrice;
    private User user;
}
