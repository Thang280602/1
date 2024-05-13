package com.shop.shoes.util;

import com.shop.shoes.dto.ColorDTO;
import com.shop.shoes.model.Color;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ColorUtils {
    private final ModelMapper modelMapper;
    public Color mapColorDTOtoColor(ColorDTO colorDTO){
        return modelMapper.map(colorDTO, Color.class);
    }
    public ColorDTO mapColortoColorDTO(Color color) {
        return modelMapper.map(color, ColorDTO.class);
    }
}
