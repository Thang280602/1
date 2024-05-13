package com.shop.shoes.service.Impl;

import com.shop.shoes.dto.SizeDTO;
import com.shop.shoes.exception.domain.ExceptionMessage;
import com.shop.shoes.model.Size;
import com.shop.shoes.repository.SizeRepository;
import com.shop.shoes.service.SizeSevice;
import com.shop.shoes.util.SizeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeSevice {
    @Autowired
    private final SizeRepository sizeRepository;
    @Autowired
    private final SizeUtils sizeUtils;

    public SizeServiceImpl(SizeRepository sizeRepository, SizeUtils sizeUtils) {
        this.sizeRepository = sizeRepository;
        this.sizeUtils = sizeUtils;
    }

    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Size create(SizeDTO sizeDTO) {
        Size size = sizeUtils.mapSizeDTOtoSize(sizeDTO);
        try {
            Size sizeSave = sizeRepository.save(size);
            return sizeSave;
        }catch (Exception e){
            throw new ExceptionMessage("Không thêm được size");
        }
    }
}
