package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class UserController {
    
    private static final String REST_URL = "api/user";
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/me")
    public ResponseEntity getCurrentUser(){
        return new ResponseEntity(userService.getCurrentUser(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{userId}")
    public ResponseEntity getCurrentUser(@PathVariable String userId){
        return new ResponseEntity(userService.getUser(userId), HttpStatus.OK);
    }
    
    @PutMapping(value = "/update")
    public ResponseEntity updateUser(@RequestBody User user){
        return userService.updateUser(user)!=null?new ResponseEntity(HttpStatus.OK):new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    
    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/{userId}/role/change/{role}")
    public ResponseEntity changeUserRole(@PathVariable String role, @PathVariable String userId, @RequestParam("country") String country){
        return new ResponseEntity(userService.changeRole(userId, Role.valueOf(role), Country.valueOf(country)), HttpStatus.OK);
    }
    
    @PostMapping(value = "/role/{role}/request")
    public ResponseEntity requestToChangeRole(@PathVariable String role, @RequestParam("country") String country){
        return new ResponseEntity(userService.processRequestToChangeRole(Role.valueOf(role), Country.valueOf(country)), HttpStatus.OK);
    }
}
