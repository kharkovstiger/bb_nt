package com.tiger.bb_nt.controller;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.SecUserDetailsService;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;
import com.tiger.bb_nt.security.jwt.JwtTokenUtil;
import com.tiger.bb_nt.service.JwtAuthService;
import com.tiger.bb_nt.service.UserService;
import com.tiger.bb_nt.util.UserWithJwt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRestController {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.header}")
    private String tokenHeader;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final SecUserDetailsService secUserDetailsService;

    private final JwtAuthService jwtAuthService;

    private final UserService userService;

    //String CLIENT_SECRET_FILE = "D:\\NeighBro\\server\\secret\\client_secret.json";
    private final String CLIENT_SECRET_FILE = "secret/client_secret.json";
    private final String CLIENT_ID_IOS = "88033795714-8s4o1oet0sklm2psk28oksq3j5fcvvh9.apps.googleusercontent.com";
    private final String CLIENT_ID_ANDROID = "194299677119-htja39orrub97t9ita40dsnf253nm3mh.apps.googleusercontent.com";
    private final String CLIENT_ID_WEB = "588816424784-15reots8ukm66tedavq6g2vc98uaq8pj.apps.googleusercontent.com";

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, SecUserDetailsService secUserDetailsService, JwtAuthService jwtAuthService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.secUserDetailsService = secUserDetailsService;
        this.jwtAuthService = jwtAuthService;
        this.userService = userService;
    }

    @PostMapping(value = "${jwt.route.authentication.path}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        String emailLowerCase = authenticationRequest.getEmail().toLowerCase();
        // Perform the security
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            emailLowerCase,
                            authenticationRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        // Reload password post-security so we can generate token
        final UserDetails userDetails = secUserDetailsService.loadUserByUsername(emailLowerCase);
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        User currentUser = userService.getByEmail(emailLowerCase);
        UserWithJwt userWithJwt = new UserWithJwt(token, currentUser);
        //JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(token);

        // Return the token
        return ResponseEntity.ok(userWithJwt);
    }
}
