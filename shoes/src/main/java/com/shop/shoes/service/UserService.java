package com.shop.shoes.service;

import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.model.Order;
import com.shop.shoes.model.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    List<UserDTO> getAll();

    UserDTO getById(Long id);

    UserDTO create(UserDTO userDTO,String siteURL) throws UnsupportedEncodingException, MessagingException;

    UserDTO update(Long id,UserDTO userDTO) throws Exception;

    void deleteById(Long id);
    UserDTO findByUserName(String userName);
    void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException;
    void sendOrderConfirmationEmail(User user, Order order, String siteURL) throws MessagingException, UnsupportedEncodingException;
}
