package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.bb.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DefaultPlayerRepository implements PlayerRepository {
    
    private final CrudPlayerRepository crudPlayerRepository;

    @Autowired
    public DefaultPlayerRepository(CrudPlayerRepository crudPlayerRepository) {
        this.crudPlayerRepository = crudPlayerRepository;
    }

    @Override
    public Player save(Player player) {
        return crudPlayerRepository.save(player);
    }

    @Override
    public Player findOne(String id) {
        return crudPlayerRepository.findById(id).orElse(null);
    }
}
