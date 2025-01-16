package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = PostController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class PostController {
    static final String REST_URL = "api/post";
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "/players")
    public ResponseEntity getPosts(@RequestParam("country") String country){
        return new ResponseEntity(postService.getPostsForCountry(Country.valueOf(country)), HttpStatus.OK);
    }
}
