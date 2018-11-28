package com.tiger.bb_nt.model;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_U21NT,
    ROLE_NT,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
