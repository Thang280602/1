package com.shop.shoes.repository;

import com.shop.shoes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsUsersByUsername(String username);
    Optional<User> findByUsername(String username);
}
