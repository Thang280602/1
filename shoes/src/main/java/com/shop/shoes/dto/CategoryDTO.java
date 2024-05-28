package com.shop.shoes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private boolean categoryStatus;
    private String categoryName;

    public CategoryDTO(String categoryName, Boolean categoryStatus) {
        this.categoryName=categoryName;
        this.categoryStatus=categoryStatus;
    }
}
