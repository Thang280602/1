package com.shop.shoes.controller;

import com.shop.shoes.dto.ColorDTO;
import com.shop.shoes.model.Color;
import com.shop.shoes.service.ColorService;
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
@RequestMapping("/color")
public class ColorController {
    @Autowired
    public final ColorService colorService;

    @Operation(summary = "Lấy danhh sách tất cả màu",
            description = "Trả về danh sách màu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách màu")})
    @GetMapping("")
    public ResponseEntity<List<Color>> getAll() {
        List<Color> colors = colorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(colors);
    }

    @Operation(summary = "Thêm màu sắc",
            description = "Trả về màu sắc và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về màu sắc"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate , trả về message lỗi"),
    })
    @PostMapping("/add")
    public ResponseEntity<Color> create(@Valid @RequestBody ColorDTO colorDTO) {

        Color responseDTO = colorService.create(colorDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }
}
