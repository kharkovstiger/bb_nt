package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;

import javax.security.auth.login.LoginException;

public interface UserService {
    User getByLogin(String loginLowerCase);

    User createUser(JwtAuthenticationRequest authenticationRequest) throws LoginException;
}
