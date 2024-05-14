package com.shop.shoes.service.Impl;

import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.Category;
import com.shop.shoes.model.Product;
import com.shop.shoes.repository.CategoryRepository;
import com.shop.shoes.repository.ProductRepository;
import com.shop.shoes.service.ProductService;
import com.shop.shoes.util.ProductUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    public final ProductRepository productRepository;
    @Autowired
    public final ProductUtils productUtils;
    @Autowired
    public final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductUtils productUtils, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productUtils = productUtils;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public  Product findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ExceptionMessage("Không tìm thấy product có id trên"));
        return product;
    }

    @Override
    public Product createProduct(ProductDTO productDTO,String fileName) {

        Product product = productUtils.mapProductDtotoProduct(productDTO);
        Optional<Category> category = categoryRepository.findById(product.getCategory().getId());
        if (category.isPresent()) {
            Product productSave = new Product();
            productSave.setCategory(category.get());
            productSave.setProductAddress(product.getProductAddress());
            productSave.setPrice(product.getPrice());
            productSave.setDescription(product.getDescription());
            productSave.setImage(fileName);
            productSave.setProductName(product.getProductName());
            productSave.setProductStatus(product.getProductStatus());
            Product savedProduct = productRepository.save(productSave);
            return savedProduct;
        } else {
            throw new ExceptionMessage("Không thêm được product: ");
        }
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO,String fileName) {

        Product productOld = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm này không tồn tại"));

        if (!id.equals(productDTO.getId())) {
            throw new RuntimeException("Id của sản phẩm không khớp với id của sản phẩm cũ");
        }
        Category categoryByID = categoryRepository.findById(productDTO.getCategoryID())
                .orElseThrow(() -> new RuntimeException("Danh mục của sản phẩm này không tồn tại"));
        productOld.setPrice(productDTO.getPrice());
        productOld.setProductAddress(productDTO.getProductAddress());
        productOld.setDescription(productDTO.getDescription());
        productOld.setProductName(productDTO.getProductName());
        productOld.setProductStatus(productDTO.getProductStatus());
        productOld.setImage(fileName);
        productOld.setCategory(categoryByID);
        Product savedProduct = productRepository.save(productOld);
        return savedProduct;
    }

    @Override
    public List<Product> getFirst8Products() {
        return productRepository.findTop8ByOrderByIdAsc().stream()
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByProductId(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ExceptionMessage("Không thể xóa sản phẩm này");
        }
        productRepository.deleteById(id);
    }
}
