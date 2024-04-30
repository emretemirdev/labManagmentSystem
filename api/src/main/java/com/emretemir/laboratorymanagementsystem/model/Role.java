package com.emretemir.laboratorymanagementsystem.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");
    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}