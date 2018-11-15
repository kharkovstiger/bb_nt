package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = PlayerController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class PlayerController {
    static final String REST_URL = "api/player";
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }
    
    @PutMapping(value = "/add")
    public ResponseEntity addPlayer(@RequestBody String playerId){
        return playerService.addPlayer(playerId)?new ResponseEntity(HttpStatus.OK):new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}