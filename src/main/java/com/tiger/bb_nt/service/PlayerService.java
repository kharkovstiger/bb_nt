package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.bb.Player;

import java.util.List;

public interface PlayerService {
    boolean addPlayer(String playerId);

    List<Player> getTeamPlayers();

    List<Player> getTeamPlayersForCurrentCountry();
}
