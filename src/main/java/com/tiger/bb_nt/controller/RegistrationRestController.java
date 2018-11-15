package com.tiger.bb_nt.controller;

import com.mealdeal.prototype.service.user.UserService;
import com.mealdeal.prototype.to.UserForRegistration;
import com.mealdeal.prototype.to.UserWithJwt;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.SecUserDetailsService;
import com.tiger.bb_nt.security.jwt.JwtTokenUtil;
import com.tiger.bb_nt.util.UserWithJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = RegistrationRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationRestController {

    static final String REST_URL = "/api";

    private final UserService userService;

    private final JwtTokenUtil jwtTokenUtil;

    private final SecUserDetailsService secUserDetailsService;

    @Autowired
    public RegistrationRestController(UserService userService, JwtTokenUtil jwtTokenUtil, SecUserDetailsService secUserDetailsService) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.secUserDetailsService = secUserDetailsService;
    }

//    @Secured("ROLE_CUSTOMER")
    @PostMapping(value = "/customer/registration", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomerUser(@Valid @RequestBody UserForRegistration customerUser, Device device) {
        String email = customerUser.getEmail();
//        boolean valid = org.apache.commons.validator.EmailValidator.getInstance().isValid(email);
//        if (!valid) {
//            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//        }
        User createdUser = userService.createCustomerUser(customerUser);
        if (createdUser == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if(!createdUser.isEnabled()){
            //TODO something with
            return new ResponseEntity<>(new UserWithJwt(null, createdUser), HttpStatus.CREATED);
        }
        final UserDetails userDetails = secUserDetailsService.loadUserByUsername(email);
        final String token = jwtTokenUtil.generateToken(userDetails, device);

        UserWithJwt userWithJwt = new UserWithJwt(token, createdUser);

        // Return the token
        return new ResponseEntity<>(userWithJwt, HttpStatus.CREATED);
    }
}
