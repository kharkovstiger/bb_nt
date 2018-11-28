package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;

import javax.security.auth.login.LoginException;

public interface UserService {
    User getByLogin(String loginLowerCase);

    User createUser(JwtAuthenticationRequest authenticationRequest) throws LoginException;

    User getCurrentUser();

    void afterLogin();

    boolean tryTologin(String login, String code);

    User updateUser(User currentUser);

    User getUser(String userId);

    User changeRole(String userId, Role role, Country country);

    User processRequestToChangeRole(Role role, Country country);
}
