package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;

public interface UserService {
    User getByLogin(String loginLowerCase);

    User createUser(JwtAuthenticationRequest authenticationRequest);
}
