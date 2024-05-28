package com.shop.shoes.controller;

import com.shop.shoes.dto.CategoryDTO;
import com.shop.shoes.dto.ProductDTO;
import com.shop.shoes.model.Category;
import com.shop.shoes.service.Impl.CSVImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
public class CSVImportController {
    @Autowired
    private CSVImportService csvService;

    @PostMapping("/import/{type}")
    public <T> ResponseEntity<?> importCSV(@RequestParam("file") MultipartFile file, @PathVariable String type) {
        Class<T> clazz = getClassByType(type);
        try {
            List<T> results = csvService.parseCSV(file, clazz);
            return ResponseEntity.ok(results);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the file.");
        }
    }

    private <T> Class<T> getClassByType(String type) {
        switch (type.toLowerCase()) {
            case "category":
                return (Class<T>) Category.class;
            case "product":
                return (Class<T>) ProductDTO.class;
//            case "productdetail":
//                return (Class<T>) ProductDetail.class;
            default:
                throw new IllegalArgumentException("Unknown type: " + type);
        }
    }
}
