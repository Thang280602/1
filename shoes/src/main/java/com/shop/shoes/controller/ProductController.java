package com.shop.shoes.controller;

import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Product;
import com.shop.shoes.service.ProductService;
import com.shop.shoes.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    public final ProductService productService;
    @Autowired
    public final StorageService storageService;

    @Operation(summary = "Lấy danhh sách tất cả sản phẩm",
            description = "Trả về danh sách sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách sản phẩm")})
    @GetMapping("")
    public ResponseEntity<Page<Product>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.getAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(productPage);
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

    @Operation(summary = "Lấy danh sách 8 sản phẩm",
            description = "Trả về danh sách  sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách chi tiết sản phẩm")})
    @GetMapping("/getFirst8Products")
    public ResponseEntity<List<Product>> getFirst8Products() {
        List<Product> productDetailDTO = productService.getFirst8Products();
        return ResponseEntity.status(HttpStatus.OK).body(productDetailDTO);
    }

    @Operation(summary = "Thêm sản phẩm",
            description = "Trả về sản phẩm và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về sản phẩm"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate , trả về message lỗi"),
    })
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<Product> create(@Valid @ModelAttribute ProductDTO productDTO, @RequestParam("fileImage") MultipartFile file) {
        this.storageService.store(file);
        String fileName = file.getOriginalFilename();
        Product responseDTO = productService.createProduct(productDTO, fileName);

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
    public ResponseEntity<Product> updateProduct(@Valid @ModelAttribute ProductDTO productDTO, @PathVariable Long id, @RequestParam("fileImage") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        this.storageService.store(file);
        Product reponseDTO = productService.updateProduct(id, productDTO, fileName);
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
