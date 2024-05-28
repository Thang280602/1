package com.shop.shoes.service.Impl;

import com.opencsv.CSVReader;
import com.shop.shoes.dto.CategoryDTO;
import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Category;
import com.shop.shoes.model.Product;
import com.shop.shoes.model.ProductDetail;
import com.shop.shoes.repository.CategoryRepository;
import com.shop.shoes.repository.ProductDetailRepository;
import com.shop.shoes.repository.ProductRepository;
import com.shop.shoes.service.CategoryService;
import com.shop.shoes.service.ProductService;
import com.shop.shoes.util.CategoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVImportService {
    private static final int MAX_RECORDS = 10;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryUtils categoryUtils;
    public <T> List<T> parseCSV(MultipartFile file, Class<T> clazz) throws Exception {
        List<T> results = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;
            int recordCount = 0;
            while ((nextLine = reader.readNext()) != null) {
                if (recordCount >= MAX_RECORDS) {
                    throw new IllegalArgumentException("File contains more than " + MAX_RECORDS + " records.");
                }
                T obj = mapToObject(nextLine, clazz);
                if (obj != null) {
                    results.add(obj);
                }
                recordCount++;
            }
        }
//         Lưu dữ liệu vào cơ sở dữ liệu
        saveToDatabase(results);
        return results;
    }

    private <T> T mapToObject(String[] data, Class<T> clazz) {
        try {
            if (clazz == Category.class) {
                Boolean categoryStatus = Boolean.parseBoolean(data[1]);
                String categoryName = data[0];
                return clazz.cast(new Category(categoryName, categoryStatus));
            }
            else if (clazz == ProductDTO.class) {
                String image = data[0];
                Double price = Double.parseDouble(data[1]);
                String productName = data[2];
                String productAddress = data[3];
                String description = data[4];
                Boolean status=Boolean.parseBoolean(data[5]);
                Long categoryID = Long.parseLong(data[6]);
                return clazz.cast(new ProductDTO(image,price,productName,productAddress,description,status,categoryID));}
//            } else if (clazz == ProductDetail.class) {
//                return clazz.cast(new ProductDetail(data[0], data[1], data[2], data[3]));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> void saveToDatabase(List<T> results) {
        for (T obj : results) {
            if (obj instanceof Category) {
                Category dto = (Category) obj;

                categoryRepository.save(dto);
            }
            else if (obj instanceof ProductDTO) {
                Product product = new Product();
                product.setImage(((ProductDTO) obj).getImage());
                product.setPrice(((ProductDTO) obj).getPrice());
                product.setProductAddress(((ProductDTO) obj).getProductAddress());
                product.setProductName(((ProductDTO) obj).getProductName());
                product.setProductStatus(((ProductDTO) obj).getProductStatus());
                product.setDescription(((ProductDTO) obj).getDescription());
                CategoryDTO categoryDTO = categoryService.findCategoryById(((ProductDTO) obj).getCategoryID());
                Category category=  categoryUtils.mapCategoryDtoToCategory(categoryDTO);
                product.setCategory(category);

                productRepository.save(product);
            } else if (obj instanceof ProductDetail) {
                ProductDetail dto = (ProductDetail) obj;

                productDetailRepository.save(dto);
            }
        }
    }
}
