package com.shop.shoes.util;

import com.shop.shoes.dto.SizeDTO;
import com.shop.shoes.model.Size;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SizeUtils {
    private final ModelMapper modelMapper;

    public Size mapSizeDTOtoSize(SizeDTO sizeDTO) {
        return modelMapper.map(sizeDTO, Size.class);
    }

    public SizeDTO mapSizetoSizeDTO(Size size) {
        return modelMapper.map(size, SizeDTO.class);
    }
}
