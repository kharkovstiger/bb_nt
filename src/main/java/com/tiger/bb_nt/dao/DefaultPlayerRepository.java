package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.bb.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Player> findPlayersForNT(boolean u21, Country roleCountry) {
        return u21?crudPlayerRepository.findPlayersForU21NT(roleCountry):crudPlayerRepository.findPlayersForNT(roleCountry);
    }

    @Override
    public boolean exist(String id) {
        return crudPlayerRepository.existsById(id);
    }

    @Override
    public void saveAll(List<Player> players) {
        crudPlayerRepository.saveAll(players);
    }

    @Override
    public void deleteAll(List<Player> players) {
        crudPlayerRepository.deleteAll(players);
    }

    @Override
    public void deleteById(String playerId) {
        crudPlayerRepository.deleteById(playerId);
    }
}
