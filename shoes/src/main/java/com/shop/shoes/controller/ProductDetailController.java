package com.shop.shoes.controller;

import com.shop.shoes.dto.ImageProductDTO;
import com.shop.shoes.dto.ProductDetailDTO;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.ImageProduct;
import com.shop.shoes.model.ProductDetail;
import com.shop.shoes.service.ImageProductSevice;
import com.shop.shoes.service.ProductDetailService;
import com.shop.shoes.service.StorageService;
import com.shop.shoes.util.ImageProductUtils;
import com.shop.shoes.util.ProductDetailUtils;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/productDetail")
public class ProductDetailController {

    @Autowired
    public final ProductDetailService productDetailService;
    @Autowired
    public final StorageService storageService;
    @Autowired
    public final  ImageProductSevice imageProductSevice;
    @Autowired
    public final ImageProductUtils imageProductUtils;
    @Autowired
    public final ProductDetailUtils productDetailUtils;

    @Operation(summary = "Lấy danhh sách tất cả chi tiết sản phẩm",
            description = "Trả về danh sách chi tiết sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách chi tiết sản phẩm")})
    @GetMapping("")
    public ResponseEntity<List<ProductDetail>> getAll() {
        List<ProductDetail> productDetailDTO = productDetailService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(productDetailDTO);
    }
    @Operation(summary = "Lấy danhh sách tất cả chi tiết sản phẩm theo sản phẩm",
            description = "Trả về danh sách chi tiết sản phẩm theo sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách chi tiết sản phẩm theo sản phẩm")})
    @GetMapping("/findProductDetailByProductId/{id}")
    public ResponseEntity<List<ProductDetail>> getProductDetailByProductId(@PathVariable Long id) {
        List<ProductDetail> productDetailDTO = productDetailService.findProductDetailByProductId(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailDTO);
    }

    @Operation(summary = "Lấy sản phẩm theo id",
            description = "Trả về chi tiết sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách chi tiết sản phẩm")})
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetail> findById(@PathVariable Long id) {
        ProductDetail productDetailDTO = productDetailService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailDTO);
    }
    @Operation(summary = "Lấy sản phẩm theo color name và size name",
            description = "Trả về chi tiết sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách chi tiết sản phẩm")})
    @GetMapping("/findByColorNameAndSizeName")
    public ResponseEntity<ProductDetail> findByColorNameAndSizeName(@RequestParam("productId") Long id , @RequestParam("colorName") String colorName, @RequestParam("sizeName") String sizeName) {
        ProductDetail productDetailDTO = productDetailService.findProductDetailByColorNameAndSizeName(id,colorName, sizeName);
        return ResponseEntity.status(HttpStatus.OK).body(productDetailDTO);
    }
    @Operation(summary = "Lấy sản phẩm theo color name và size name",
            description = "Trả về chi tiết sản phẩm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về danh sách chi tiết sản phẩm")})
    @GetMapping("/findImageDescription")
    public ResponseEntity<List<ImageProductDTO>> findImageDiscription(@RequestParam("productId") Long id , @RequestParam("colorName") String colorName, @RequestParam("sizeName") String sizeName) {
        ProductDetail productDetailDTO = productDetailService.findProductDetailByColorNameAndSizeName(id,colorName, sizeName);
        List<ImageProductDTO> imageProductDTO = this.imageProductSevice.findByProductDetail(productDetailDTO.getId());
        return ResponseEntity.status(HttpStatus.OK).body(imageProductDTO);
    }
    @Operation(summary = "Thêm  sản phẩm chi tiết",
            description = "Trả về sản phẩm chi tiết và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trả về sản phẩm chi tiết"),
            @ApiResponse(responseCode = "404", description = "Lỗi không thỏa mãn validate , trả về message lỗi"),
    })
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<ProductDetail> create(@Valid @ModelAttribute ProductDetailDTO productDetailDTO, @RequestPart("fileImage") MultipartFile file, @RequestParam("fileImages") List<MultipartFile> files) {
        try {
            // Lưu file ảnh chính
            this.storageService.store(file);
            String fileName = file.getOriginalFilename();

            // Tạo ProductDetailDTO và lưu ProductDetail trước
            ProductDetailDTO productDetailDTOSave = new ProductDetailDTO();
            productDetailDTOSave.setImage(fileName);
            productDetailDTOSave.setProductID(productDetailDTO.getProductID());
            productDetailDTOSave.setQuantity(productDetailDTO.getQuantity());
            productDetailDTOSave.setStatus(productDetailDTO.getStatus());
            productDetailDTOSave.setDiscount(productDetailDTO.getDiscount());
            productDetailDTOSave.setSizeID(productDetailDTO.getSizeID());
            productDetailDTOSave.setColorID(productDetailDTO.getColorID());
            ProductDetail savedProductDetail = productDetailService.createProductDetail(productDetailDTOSave);
            for (MultipartFile multipartFile : files) {
                this.storageService.store(multipartFile);
                ImageProduct imageProduct = new ImageProduct();
                imageProduct.setImage(multipartFile.getOriginalFilename());

                imageProduct.setProductDetail(savedProductDetail);

                ImageProductDTO imageProductDTO = imageProductUtils.mapImageProducttoImageProductDTO(imageProduct);
                this.imageProductSevice.create(imageProductDTO);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDetail);
        } catch (Exception e) {
            throw new ExceptionMessage(e.getMessage());
        }


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
    public ResponseEntity<ProductDetail> updateProduct(@Valid @ModelAttribute ProductDetailDTO productDetailDTO, @PathVariable Long id, @RequestParam("fileImage") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        boolean isEmpty = fileName == null || fileName.trim().length() == 0;
        ProductDetailDTO productDetailDTOUpdate = new ProductDetailDTO();
        if (!isEmpty) {
            this.storageService.store(file);
            productDetailDTOUpdate.setImage(fileName);
            productDetailDTOUpdate.setId(id);
            productDetailDTOUpdate.setProductID(productDetailDTO.getProductID());
            productDetailDTOUpdate.setQuantity(productDetailDTO.getQuantity());
            productDetailDTOUpdate.setStatus(productDetailDTO.getStatus());
            productDetailDTOUpdate.setDiscount(productDetailDTO.getDiscount());
            productDetailDTOUpdate.setSizeID(productDetailDTO.getSizeID());
            productDetailDTOUpdate.setColorID(productDetailDTO.getColorID());

        }
        ProductDetail reponseDTO = productDetailService.updateProductDetail(id, productDetailDTOUpdate);
        return ResponseEntity.status(HttpStatus.OK).body(reponseDTO);
    }

    @Operation(summary = "Xóa sản phẩm theo ID",
            description = "Trả về sản phẩm và thông tin message trạng thái")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Trả về sản phẩm đã được xóa"),
    })
    @Parameters(@Parameter(name = "id", description = "id của sản phẩm cần xóa"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ProductDetail> deleteProduct(@PathVariable Long id) {
        productDetailService.deleteByProductId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
