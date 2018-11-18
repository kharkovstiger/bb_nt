package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.bb.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CrudPlayerRepository extends MongoRepository<Player, String> {

    @Override
    Player save(Player player);

    @Override
    List<Player> findAll();

    @Override
    Optional<Player> findById(String id);

    @Override
    void deleteById(String id);
}
