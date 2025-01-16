package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.model.Country;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = CountryController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

    static final String REST_URL = "api/country";

    @PutMapping(value = "/choose/{country}")
    public ResponseEntity chooseCountry(HttpServletRequest request, HttpServletResponse response, @PathVariable Country country,
                                        HttpSession session){
//        Cookie countryCookie=new Cookie("country", country.name());
//        response.addCookie(countryCookie);
        session.setAttribute("country", country);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @GetMapping(value = "/get")
    public ResponseEntity getSiteCountries(){
        return new ResponseEntity(Country.values(), HttpStatus.OK);
    }
}
