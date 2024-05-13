package com.shop.shoes.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author ThangDH
 */
@RequiredArgsConstructor
public enum PermissionEnum {
    ADMIN_VIEW_ALL_USERS("/users",PermissionMethodEnum.GET), // xem danh sách user
    ADMIN_UPDATE_USER("/users/*",PermissionMethodEnum.PUT), // update user
    ADMIN_VIEW_USER_DETAILS("/users/*",PermissionMethodEnum.GET), // xem user chi tiết
    USER_VIEW_USER_DETAILS("/users/*",PermissionMethodEnum.GET),
    ADMIN_CREATE_CATEGORY("/category/*",PermissionMethodEnum.POST) //tạo mới danh mục
    ;
    @Getter
    private final String url;

    @Getter
    private final PermissionMethodEnum method;

}
