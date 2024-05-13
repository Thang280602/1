package com.shop.shoes.repository;

import com.shop.shoes.enumeration.RoleEnum;
import com.shop.shoes.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleEnum roleName);
}
