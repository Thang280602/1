package com.shop.shoes.repository;

import com.shop.shoes.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("select  count(*) from Cart p where p.user.id = ?1 ")
    Long countId(Long idUser);
}
