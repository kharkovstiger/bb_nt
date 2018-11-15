package com.tiger.bb_nt.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user", produces = MediaType.APPLICATION_XML_VALUE)
@CrossOrigin
public class UserController {
    
    private static final String REST_URL = "api/user";
    
    
}
