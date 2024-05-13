package com.shop.shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ThangDH
 * Class tiếp nhận req body và trả về theo formmat
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {
    private String username;
    private String authenticationCode;
}
