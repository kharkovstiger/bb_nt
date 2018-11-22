package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.model.bb.Player;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BBAPIService {
    String getBoxScore(String id, String login, String code);

    ResponseEntity<String> login(String login, String code, Integer info);

    Player getPlayer(String id, String login, String code);

    String getSeasons(String login, String code);

    List<Player> getPlayers(String login, String code);
}
