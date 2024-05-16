package com.shop.shoes.controller;

import com.shop.shoes.dto.AuthDTO;
import com.shop.shoes.dto.TokenDTO;
import com.shop.shoes.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ThangDH
 * Class để test api với spring security jwt
 */

@RestController
@RequestMapping("/auth")
public class JwtProviderController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Operation(summary = "Phương thức để như tính năng login xác thực người dùng",
            description = "Trả về token nếu thành công")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về token"),
            @ApiResponse(responseCode = "404", description = "Lỗi không tìm thấy người dùng với username vaf authenticationCode hợp lệ"),
    })
    @Parameters(@Parameter(name = "AuthDTO.class",description = "Gửi lên 2 trường username và pass"))
    @PostMapping("/generateToken")
    public ResponseEntity<TokenDTO> authenticateAndGetToken(@RequestBody AuthDTO authDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getAuthenticationCode())
        );
        if (authentication.isAuthenticated()) {
            String role = authentication.getAuthorities().iterator().next().getAuthority();
//            Long userId = userService.getUserIdByUsername(userDetails.getUsername());
            TokenDTO tokenDTO = jwtService.generateToken(authDTO.getUsername(),role);
            return ResponseEntity.status(HttpStatus.OK).body(tokenDTO);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
