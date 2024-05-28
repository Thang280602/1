package com.shop.shoes.controller;


import com.shop.shoes.constant.UserConstant;
import com.shop.shoes.dto.UserDTO;
import com.shop.shoes.enumeration.RoleEnum;
import com.shop.shoes.exception.domain.UserNotFoundException;
import com.shop.shoes.security.CustomUserDetails;
import com.shop.shoes.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author ThangDH
 * Class xử lý tiếp nhận request của User từ client
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final String ROLE_ADMIN = RoleEnum.ADMIN.toString();
    @Autowired
    private MessageSource messageSource;
    // get all

    /**
     * Lấy tất danh sách User
     *
     * @return
     */
    @Operation(summary = "Lấy danhh sách tất cả người dùng",
            description = "Trả về danh sách USER nếu thành công")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách ngời dùng"),
            @ApiResponse(responseCode = "403", description = "không có quyền"),
            @ApiResponse(responseCode = "400", description = "Sai đường dẫn truy cập"),
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userDTOS = userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }

    // get by id

    /**
     * @param id
     * @return
     */
    @Operation(summary = "Lấy User theo ID",
            description = "Trả về người dùng và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về người dùng và ca thông tin khác"),
            @ApiResponse(responseCode = "404", description = "Lỗi không tìm thấy người dùng với id được gửi"),
    })
    @Parameters(@Parameter(name = "id", description = "ID của User"))
    @GetMapping("{id}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> getById(@PathVariable Optional<Long> id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        List<RoleEnum> roles = customUserDetails.getUser().getRoles().stream().map(role -> role.getRoleName()).toList();

        if (roles.contains(RoleEnum.USER) && (!id.get().equals(customUserDetails.getUser().getId()))) {
            throw new UserNotFoundException(messageSource.getMessage(UserConstant.USER_MESSAGE_NOT_FOUND,null , Locale.getDefault()));
        }
        UserDTO userDTO = userService.getById(id.orElseThrow(() -> new UserNotFoundException("ID không hợp lệ")));
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/getByUserName")
    public ResponseEntity<UserDTO> getByUserName(@RequestParam("userName") String userName) {
        UserDTO userDTO = userService.findByUserName(userName);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }


    // insert
    @Operation(summary = "Thêm User ",
            description = "Trả về người dùng và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về người dùng và ca thông tin khác"),
            @ApiResponse(responseCode = "403", description = "không có quyền"),
            @ApiResponse(responseCode = "400", description = "Sai đường dẫn truy cập"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate. Tra về message lỗi"),

    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> insertUser(@Validated(ValidationGroups.Insert.class) @RequestBody UserDTO userDTO, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

        UserDTO userDTONew = userService.create(userDTO, getSiteURL(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTONew);
    }

    // update
    @Operation(summary = "Update User theo ID",
            description = "Trả về người dùng và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về người dùng và ca thông tin khác"),
            @ApiResponse(responseCode = "403", description = "không có quyền"),
            @ApiResponse(responseCode = "400", description = "Sai đường dẫn truy cập"),
            @ApiResponse(responseCode = "404", description = "Lỗi không tìm thấy người dùng hoặc không thỏa mãn validate với id được gửi"),
    })

    @Parameters({@Parameter(name = "UserDTO", description = "thông tin cần update của người dùng"),
            @Parameter(name = "id", description = "id của user câần update")})
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UserDTO> updateUser(@Validated(ValidationGroups.Update.class) @RequestBody UserDTO userDTO, @PathVariable Long id) throws Exception {
        UserDTO userDTO1 = userService.update(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO1);
    }

    // delete
    @Operation(summary = "Xóa User theo ID",
            description = "Trả về người dùng và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trả về người dùng đã được xóa"),
            @ApiResponse(responseCode = "403", description = "không có quyền"),
            @ApiResponse(responseCode = "400", description = "Sai đường dẫn truy cập"),
    })
    @Parameters(@Parameter(name = "id", description = "id của user cần update"))
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<UserDTO> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
