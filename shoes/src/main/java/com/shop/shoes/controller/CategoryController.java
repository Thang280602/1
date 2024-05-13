package com.shop.shoes.controller;

import com.shop.shoes.dto.CategoryDTO;
import com.shop.shoes.model.Category;
import com.shop.shoes.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    public final CategoryService categoryService;

    @Operation(summary = "Lấy danhh sách tất cả danh muc",
            description = "Trả về danh sách danh muc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách danh muc")})
    @GetMapping("")
    public ResponseEntity<List<Category>> getAll() {
        List<Category> categoryDTOS = categoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTOS);
    }
    @Operation(summary = "Lấy  danh muc theo id",
            description = "Trả về danh sách danh muc")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách danh muc")})
    @GetMapping("/get/{id}")
    public ResponseEntity<Category> getCategoryByID(@PathVariable  Long id) {
        Category categoryDTOS = categoryService.findCategoryById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTOS);
    }

    @Operation(summary = "Thêm danh mục",
            description = "Trả về danh mục và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh mục"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate , trả về message lỗi"),
    })

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<Category> create(@Valid @RequestBody CategoryDTO categoryDTO) {

        Category responseDTO = categoryService.createCategory(categoryDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @Operation(summary = "Cập nhật danh mục theo ID",
            description = "Trả về danh mục và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh mục"),
            @ApiResponse(responseCode = "404", description = "Lỗi không tìm thấy danh mục hoặc không thỏa mãn validate với id được gửi"),
    })
    @Parameters({@Parameter(name = "CategoryDTO", description = "Thông tin cần update "),
            @Parameter(name = "id", description = "id của danh muc  cần update")})
    @PutMapping("/update/{id}")
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long id) {
        Category reponseDTO = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(reponseDTO);
    }

    @Operation(summary = "Xóa danh mục theo ID",
            description = "Trả về danh mục và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trả về danh mục đã được xóa"),
    })
    @Parameters(@Parameter(name = "id", description = "id của danh mục cần xóa"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
