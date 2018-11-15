package com.tiger.bb_nt.model;

import org.springframework.security.core.GrantedAuthority;

public enum  Role implements GrantedAuthority {
    USER,
    U21NT,
    NT,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
