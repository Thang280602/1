package com.shop.shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * format kiểu trả về của token
 * @author ThangDH
 */
@Builder
@Data
@AllArgsConstructor
public class TokenDTO {
    private String token;
}
