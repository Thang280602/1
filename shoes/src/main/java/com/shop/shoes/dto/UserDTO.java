package com.shop.shoes.dto;

import com.shop.shoes.constant.DateTimeConstant;
import com.shop.shoes.constant.UserConstant;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    @NotBlank( message = "{" + UserConstant.FIRST_NAME_NOT_BLANK + "}")
    private String firstName;

    @NotBlank( message = "{" + UserConstant.LAST_NAME_NOT_BLANK + "}")
    private String lastName;

    @NotBlank( message = "{" + UserConstant.PHONE_NUMBER_NOT_BLANK + "}")
    private String phoneNumber;

    private Boolean enabled;

    private String verificationCode;

    @DateTimeFormat(pattern = DateTimeConstant.DD_MM_YYYY)
    private LocalDate dateOfBirth;

    @Email(message = "{" + UserConstant.EMAIL_NOT_VALID + "}")
    private String email;

    @NotBlank(message = UserConstant.USERNAME_NOT_BLANK)
    private String username;

    @NotBlank(message = "{" + UserConstant.AUTHENTICATION_CODE_NOT_BLANK + "}")
    @Size(min = 8, message = "{" + UserConstant.AUTHENTICODE_NOT_VALID + "}")
    @Pattern(regexp = ".*[A-Z].*", message = "{" + UserConstant.AUTHENTICODE_NOT_VALID + "}")
    @Pattern(regexp = ".*[a-z].*", message = "{" + UserConstant.AUTHENTICODE_NOT_VALID + "}")
    @Pattern(regexp = ".*\\d.*", message = "{" + UserConstant.AUTHENTICODE_NOT_VALID + "}")
    @Pattern(regexp = ".*[!@#$%^&*()].*", message = "{" + UserConstant.AUTHENTICODE_NOT_VALID + "}")
    private String authenticationCode;

    private List<RoleDTO> roles;

}
