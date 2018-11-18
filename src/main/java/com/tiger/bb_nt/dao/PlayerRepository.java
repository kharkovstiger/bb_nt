package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.bb.Player;

import java.util.List;

public interface PlayerRepository {
    
    Player save(Player player);
    
    Player findOne(String id);
}
