package com.shop.shoes.service.Impl;

import com.shop.shoes.constant.CategoryConstant;
import com.shop.shoes.dto.ColorDTO;
import com.shop.shoes.exception.domain.CategoryNotFoundException;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.Category;
import com.shop.shoes.model.Color;
import com.shop.shoes.repository.ColorRepository;
import com.shop.shoes.service.ColorService;
import com.shop.shoes.util.ColorUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ColorServiceImpl implements ColorService {
    @Autowired
    private final ColorRepository colorRepository;
    @Autowired
    private final ColorUtils colorUtils;

    public ColorServiceImpl(ColorRepository colorRepository, ColorUtils colorUtils) {
        this.colorRepository = colorRepository;
        this.colorUtils = colorUtils;
    }

    @Override
    public List<Color> getAll() {
        return colorRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Color create(ColorDTO colorDTO) {
        Color color = colorUtils.mapColorDTOtoColor(colorDTO);
        try {
            Color colorSave = colorRepository.save(color);
            return colorSave;
        }catch (Exception e){
            throw new ExceptionMessage("Không thêm được color");
        }
    }
}