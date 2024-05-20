package com.shop.shoes.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

/**
 * @author ThangDH
 * provider Role in this project
 */
@RequiredArgsConstructor
public enum RoleEnum {
    ADMIN(Set.of(
            PermissionEnum.ADMIN_UPDATE_USER,
            PermissionEnum.ADMIN_VIEW_USER_DETAILS,
            PermissionEnum.ADMIN_VIEW_ALL_USERS,
            PermissionEnum.ADMIN_CREATE_CATEGORY,
            PermissionEnum.USER_VIEW_USER_GETBYUSERNAME

    )),
    USER(Set.of(
            PermissionEnum.USER_VIEW_USER_DETAILS,PermissionEnum.USER_VIEW_USER_GETBYUSERNAME))

    ;

    @Getter
    private final Set<PermissionEnum> permissions;


}
