package com.shop.shoes.service.Impl;

import com.shop.shoes.dto.ProductDetailDTO;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.ProductDetail;
import com.shop.shoes.repository.ProductDetailRepository;
import com.shop.shoes.service.ProductDetailService;
import com.shop.shoes.util.ProductDetailUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    public final ProductDetailRepository productDetailRepository;
    @Autowired
    public final ProductDetailUtils productDetailUtils;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository, ProductDetailUtils productDetailUtils) {
        this.productDetailRepository = productDetailRepository;
        this.productDetailUtils = productDetailUtils;
    }

    @Override
    public List<ProductDetail> getAll() {
        return productDetailRepository.findAll().stream()
              .collect(Collectors.toList());
    }

    @Override
    public ProductDetail findById(Long id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(() -> new ExceptionMessage("Không tìm thấy product có id trên"));
        return productDetail;
    }

    @Override
    public ProductDetail createProductDetail(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = productDetailUtils.mapProductDetailDTOtoProductDetail(productDetailDTO);
        try {
            ProductDetail productDetailSave = productDetailRepository.save(productDetail);
            return productDetailSave;
        } catch (Exception e) {
            throw new ExceptionMessage("Không thêm được product");

        }
    }

    @Override
    public ProductDetail updateProductDetail(Long id, ProductDetailDTO productDetailDTO) {
        if (id != productDetailDTO.getId()) {
            throw new ExceptionMessage("Sản phẩm này không tồn tại");
        }
        ProductDetail productDetail = productDetailUtils.mapProductDetailDTOtoProductDetail(productDetailDTO);

        try {
            ProductDetail productDetailSave = productDetailRepository.save(productDetail);
            return productDetailSave;
        } catch (DataIntegrityViolationException e) {
            throw new ExceptionMessage("Lỗi không thêm được product");
        }
    }

    @Override
    public List<ProductDetail> findProductDetailByProductId(Long id) {
        return productDetailRepository.findProductDetailByProductId(id).stream().collect(Collectors.toList());
    }

    @Override
    public ProductDetail findProductDetailByColorNameAndSizeName(Long id, String colorName, String sizeName) {
            return productDetailRepository.findProductDetailByColorNameAndSizeName(id,colorName,sizeName);
    }


    @Override
    public void deleteByProductId(Long id) {
        Optional<ProductDetail> productDetail = productDetailRepository.findById(id);
        if (!productDetail.isPresent()) {
            throw new ExceptionMessage("Không thể xóa sản phẩm này");
        }
        productDetailRepository.deleteById(id);
    }
}
