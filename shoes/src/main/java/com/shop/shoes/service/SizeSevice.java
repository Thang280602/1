package com.shop.shoes.service;

import com.shop.shoes.dto.SizeDTO;
import com.shop.shoes.model.Size;

import java.util.List;

public interface SizeSevice {
    List<Size> getAll();
    Size create(SizeDTO sizeDTO);
}
