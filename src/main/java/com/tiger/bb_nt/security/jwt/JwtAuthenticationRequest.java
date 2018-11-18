package com.tiger.bb_nt.security.jwt;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;

    private String login;
    private String code;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String login, String code) {
        this.setLogin(login);
        this.setCode(code);
    }
}
