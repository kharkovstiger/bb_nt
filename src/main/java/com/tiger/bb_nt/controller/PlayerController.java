package com.tiger.bb_nt.controller;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.bb.Player;
import com.tiger.bb_nt.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @PutMapping(value = "/add/{playerId}")
    public ResponseEntity addPlayer(@PathVariable String playerId){
        return playerService.addPlayer(playerId)?new ResponseEntity(HttpStatus.OK):new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/players")
    public ResponseEntity getTeamPlayers(HttpServletRequest request){
        return new ResponseEntity(playerService.getTeamPlayers(), HttpStatus.OK);
    }

    @GetMapping(value = "/playersForCurrentCountry")
    public ResponseEntity getTeamPlayersForCurrentCountry(HttpSession session, @RequestParam("country") String country){
//        Country country = (Country) session.getAttribute("country");
        return new ResponseEntity(playerService.getTeamPlayersForCurrentCountry(Country.valueOf(country)), HttpStatus.OK);
    }

    @Secured({"ROLE_NT", "ROLE_U21NT"})
    @PutMapping(value = "/addBio/{playerId}")
    public ResponseEntity addBio(@PathVariable String playerId, @RequestBody String bio){
        return new ResponseEntity(playerService.addBio(playerId, bio), HttpStatus.OK);
    }

    @Secured({"ROLE_NT", "ROLE_U21NT"})
    @GetMapping(value = "/get")
    public ResponseEntity getPlayersForNT(){
        return new ResponseEntity(playerService.getPlayersForNT(), HttpStatus.OK);
    }

    @Secured({"ROLE_NT", "ROLE_U21NT"})
    @GetMapping(value = "/get/{playerId}")
    public ResponseEntity getPlayerForNT(@PathVariable String playerId){
        return new ResponseEntity(playerService.getPlayerForNT(playerId), HttpStatus.OK);
    }

    @Secured({"ROLE_NT","ROLE_U21NT"})
    @PutMapping(value = "/add")
    public ResponseEntity addPlayers(@RequestBody List<Player> players){
        playerService.addPlayers(players);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured({"ROLE_NT","ROLE_U21NT"})
    @DeleteMapping(value = "/delete/{playerId}")
    public ResponseEntity deletePlayer(@PathVariable String playerId){
        playerService.deletePlayers(playerId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Secured({"ROLE_NT","ROLE_U21NT"})
    @PutMapping(value = "/update/")
    public ResponseEntity updatePlayer(@RequestBody Player player){
        playerService.addPlayer(player);
        return new ResponseEntity(HttpStatus.OK);
    }
}
