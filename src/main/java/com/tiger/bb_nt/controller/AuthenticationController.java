package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.AuthorizedUser;
import com.tiger.bb_nt.security.SecUserDetailsService;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;
import com.tiger.bb_nt.security.jwt.JwtTokenUtil;
import com.tiger.bb_nt.service.BBAPIService;
import com.tiger.bb_nt.service.UserService;
import com.tiger.bb_nt.util.UserWithJwt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${jwt.header}")
    private String tokenHeader;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final SecUserDetailsService secUserDetailsService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                    SecUserDetailsService secUserDetailsService, UserService userService, BBAPIService bbapiService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.secUserDetailsService = secUserDetailsService;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) {
        String loginLowerCase = authenticationRequest.getLogin().toLowerCase();
        User currentUser = userService.getByLogin(loginLowerCase);
        
        if (currentUser!=null) {
            // Perform the security
            try {
                //I can't encode the "password", i need it
//                final Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                loginLowerCase,
//                                authenticationRequest.getCode()
//                        )
//                );
                if (!currentUser.getCode().equals(authenticationRequest.getCode())){
                    boolean auth=userService.tryTologin(currentUser.getLogin(), currentUser.getCode());
                    if (!auth)
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                    else {
                        currentUser.setCode(authenticationRequest.getCode());
                        currentUser=userService.updateUser(currentUser);
                    }
                } else {
                    Authentication authentication=new UsernamePasswordAuthenticationToken(new AuthorizedUser(currentUser),
                            null, currentUser.getRoles());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                
            } catch (BadCredentialsException e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            try {
                currentUser=userService.createUser(authenticationRequest);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        // Reload code post-security so we can generate token
        final UserDetails userDetails = secUserDetailsService.loadUserByUsername(loginLowerCase);
        final String token = jwtTokenUtil.generateToken(userDetails);
        
        UserWithJwt userWithJwt = new UserWithJwt(token, currentUser);
        
//        userService.afterLogin();
        
        // Return the token
        return ResponseEntity.ok(userWithJwt);
    }
}
