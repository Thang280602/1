package com.shop.shoes.service;

import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<UserDTO> getAll();

    UserDTO getById(Long id);

    UserDTO create(UserDTO userDTO);

    UserDTO update(Long id,UserDTO userDTO) throws Exception;

    void deleteById(Long id);

    void register(UserDTO user, String siteURL) throws UnsupportedEncodingException, MessagingException;
    void sendVerificationEmail(UserDTO user, String siteURL) throws MessagingException, UnsupportedEncodingException;
}
