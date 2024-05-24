package com.shop.shoes.service;

import com.shop.shoes.dto.ColorDTO;
import com.shop.shoes.model.Color;

import java.util.List;

public interface ColorService {
    List<Color> getAll();

    Color create(ColorDTO colorDTO);
}
