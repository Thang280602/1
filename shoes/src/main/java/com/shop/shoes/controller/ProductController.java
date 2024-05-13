package com.shop.shoes.controller;

import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Product;
import com.shop.shoes.service.ProductService;
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


@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public final ProductService productService;

    @Operation(summary = "Lấy danhh sách tất cả sản phẩm",
            description = "Trả về danh sách sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách sản phẩm")})
    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {
        List<Product> productDTO = productService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
    @Operation(summary = "Lấy sản phẩm có id ",
            description = "Trả về danh sách sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách sản phẩm")})
    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product productDTO = productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @Operation(summary = "Thêm sản phẩm",
            description = "Trả về sản phẩm và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về sản phẩm"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate , trả về message lỗi"),
    })
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductDTO productDTO) {

        Product responseDTO = productService.createProduct(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

    }

    @Operation(summary = "Cập nhật product theo ID",
            description = "Trả về sản phẩm và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về sản phẩm"),
            @ApiResponse(responseCode = "404", description = "Lỗi không tìm thấy sản phẩm hoặc không thỏa mãn validate với id được gửi"),
    })
    @Parameters({@Parameter(name = "ProductDTO", description = "Thông tin cần update "),
            @Parameter(name = "id", description = "id của sản phẩm  cần update")})
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long id) {
        Product reponseDTO = productService.updateProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(reponseDTO);
    }

    @Operation(summary = "Xóa sản phẩm theo ID",
            description = "Trả về sản phẩm và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trả về sản phẩm đã được xóa"),
    })
    @Parameters(@Parameter(name = "id", description = "id của sản phẩm cần xóa"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        productService.deleteByProductId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
