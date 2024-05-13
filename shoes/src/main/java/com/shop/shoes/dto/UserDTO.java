package com.shop.shoes.dto;

import com.shop.shoes.constant.DateTimeConstant;
import com.shop.shoes.constant.UserConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;


    private String phoneNumber;
    private Boolean enabled;
    private String verificationCode;
    @DateTimeFormat(pattern = DateTimeConstant.DD_MM_YYYY)
    private LocalDate dateOfBirth;

    @Email(message =  UserConstant.EMAIL_NOT_VALID )
    private String email;

    @NotBlank( message =UserConstant.USERNAME_NOT_BLANK )
    private String username;

    @NotBlank( message = UserConstant.AUTHENTICATION_CODE_NOT_BLANK )
    private String authenticationCode; // password

    private List<RoleDTO> roles;


}
