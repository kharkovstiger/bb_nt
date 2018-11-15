package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.security.AuthorizedUser;
import com.tiger.bb_nt.security.SecUserDetailsService;
import com.tiger.bb_nt.security.jwt.JwtTokenUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtUserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    private final JwtTokenUtil jwtTokenUtil;

    private final SecUserDetailsService secUserDetailsService;

    @Autowired
    public JwtUserRestController(JwtTokenUtil jwtTokenUtil, SecUserDetailsService secUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.secUserDetailsService = secUserDetailsService;
    }

    @GetMapping(value = "user")
    public AuthorizedUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        AuthorizedUser user = (AuthorizedUser) secUserDetailsService.loadUserByUsername(username);
        return user;
    }

}
