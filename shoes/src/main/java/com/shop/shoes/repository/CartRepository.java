package com.shop.shoes.repository;

import com.shop.shoes.model.Cart;
import com.shop.shoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select  count(*) from Cart p where p.user.id = ?1 ")
    Long countId(Long idUser);

    List<Cart> findByUser(User user);
}
