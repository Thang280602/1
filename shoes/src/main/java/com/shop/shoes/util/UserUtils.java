package com.shop.shoes.util;

import com.shop.shoes.constant.UserConstant;
import com.shop.shoes.dto.RoleDTO;
import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.model.Role;
import com.shop.shoes.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final ModelMapper modelMapper;

    /**
     * Chuyển và map từ kiểu userDTO sang User
     *
     * @param userDTO
     * @return User.class
     */
    public User mapUserDtoToUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        return user;
    }

    /**
     * Chuyển và map từ kiểu User sang userDTO
     *
     * @param user
     * @return UserDTO.class
     */
    public UserDTO mapUserToUserDto(User user) {
//        user.setAuthenticationCode(UserConstant.BLANK);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setAuthenticationCode(UserConstant.BLANK);
        return userDTO;
    }

    public List<Role> mapRoles(List<RoleDTO> roleDTOList) {
        return roleDTOList.stream()
                .map(roleDTO -> modelMapper.map(roleDTO, Role.class))
                .collect(Collectors.toList());
    }

    public boolean validateUser(UserDTO userDTO) {
        LocalDate currentDate = LocalDate.now();
        LocalDate dateOfBirth = userDTO.getDateOfBirth();
        int age = Period.between(dateOfBirth, currentDate).getYears();
        if (age < 18) {
            return false;
        }
        return true;
    }


}