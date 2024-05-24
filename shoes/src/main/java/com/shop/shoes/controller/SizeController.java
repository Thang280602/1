package com.shop.shoes.controller;

import com.shop.shoes.dto.SizeDTO;
import com.shop.shoes.model.Size;
import com.shop.shoes.service.SizeSevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/size")
public class SizeController {
    @Autowired
    public final SizeSevice sizeSevice;

    @Operation(summary = "Lấy danhh sách tất cả size",
            description = "Trả về danh sách size")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách size")})
    @GetMapping("")
    public ResponseEntity<List<Size>> getAll() {
        List<Size> sizes = sizeSevice.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(sizes);
    }

    @Operation(summary = "Thêm size",
            description = "Trả về size và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về size"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate , trả về message lỗi"),
    })
    @PostMapping("/add")
    public ResponseEntity<Size> create(@Valid @RequestBody SizeDTO sizeDTO) {

        Size responseDTO = sizeSevice.create(sizeDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }
}
