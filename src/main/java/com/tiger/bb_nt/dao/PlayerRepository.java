package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.bb.Player;

import java.util.List;

public interface PlayerRepository {
    
    Player save(Player player);
    
    Player findOne(String id);

    List<Player> findPlayersForNT(boolean u21, Country roleCountry);
    
    boolean exist(String id);

    void saveAll(List<Player> players);

    void deleteAll(List<Player> players);

    void deleteById(String playerId);
}
