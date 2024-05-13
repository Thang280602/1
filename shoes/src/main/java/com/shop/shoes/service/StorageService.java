package com.shop.shoes.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
public interface StorageService {
	void store (MultipartFile file);
	void init();
}
