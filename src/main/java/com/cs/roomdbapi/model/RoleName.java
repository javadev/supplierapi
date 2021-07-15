package com.cs.roomdbapi.model;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority {

    ROLE_ADMIN("admin"),

    ROLE_SUPPLIER_COMMON("supplier_common"),

    ROLE_SUPPLIER_ALL_PROPERTIES("supplier_all_properties");

    public String getAuthority() {
        return name();
    }

    private final String code;

    RoleName(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
