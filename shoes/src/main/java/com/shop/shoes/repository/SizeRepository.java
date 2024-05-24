package com.shop.shoes.repository;

import com.shop.shoes.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long> {
}
