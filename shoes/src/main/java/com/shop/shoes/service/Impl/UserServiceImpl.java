package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.UserConstant;
import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.enumeration.RoleEnum;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.exception.domain.UserNotFoundException;
import com.shop.shoes.model.Role;
import com.shop.shoes.model.User;
import com.shop.shoes.repository.RoleRepository;
import com.shop.shoes.repository.UserRepository;
import com.shop.shoes.service.UserService;
import com.shop.shoes.util.UserUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final UserUtils userUtils;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserUtils userUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userUtils = userUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream().map(user -> userUtils.mapUserToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getById(Long id) {

        return userRepository.findById(id).map(user -> userUtils.mapUserToUserDto(user)).orElseThrow(
                () -> new UserNotFoundException(UserConstant.USER_NOT_FOUND)
        );
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        if (userDTO.getId() != null && userRepository.existsById(userDTO.getId())) {
            throw new IllegalArgumentException("User này đã tồn tại");
        }
        List<Role> roles = Arrays.asList(roleRepository.findByRoleName(RoleEnum.USER).get());
        User user = userUtils.mapUserDtoToUser(userDTO);
        user.setAuthenticationCode(passwordEncoder.encode(userDTO.getAuthenticationCode()));
        user.setRoles(roles);
        try {
            User savedUser = userRepository.save(user);
            return userUtils.mapUserToUserDto(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException(UserConstant.USER_NOT_FOUND);
        }
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) throws Exception {
        if (!userDTO.getId().equals(id)) {
            throw new CategoryNotFoundException(UserConstant.USER_NOT_FOUND);
        }
        User user = userUtils.mapUserDtoToUser(userDTO);
        user.setAuthenticationCode(passwordEncoder.encode(userDTO.getAuthenticationCode()));

        List<Role> updatedRoles = userUtils.mapRoles(userDTO.getRoles());
        for (Role role : updatedRoles) {
            if (!roleRepository.existsById(role.getId())) {
                throw new UserNotFoundException(UserConstant.ROLE_NOT_FOUND);
            }
        }
        user.setRoles(updatedRoles);
        try {
            User saveUser = userRepository.save(user);
            return userUtils.mapUserToUserDto(saveUser);
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException(UserConstant.USER_NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException(UserConstant.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void register(UserDTO user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = new BCryptPasswordEncoder().encode(user.getAuthenticationCode());
        user.setAuthenticationCode(encodedPassword);

        String randomCode = UUID.randomUUID().toString();
        user.setVerificationCode(randomCode);
        user.setEnabled(true);
        User user1 = userUtils.mapUserDtoToUser(user);
        userRepository.save(user1);
        sendVerificationEmail(user, siteURL);
    }


    @Override
    public void sendVerificationEmail(UserDTO user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "thang.danghuu@vti.com.vn";
        String senderName = "Confirm when creating account";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Đặng Hữu Thắng";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }


}
