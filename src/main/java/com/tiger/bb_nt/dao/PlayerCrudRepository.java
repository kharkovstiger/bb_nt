package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.bb.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlayerCrudRepository extends MongoRepository<Player, String> {

    @Override
    Player save(Player player);

    @Override
    List<Player> findAll();
    
    Player findOne(String id);

    @Query(value = "{'country':?0}")
    List<Player> getAllFromCountry(String country);

    @Query(value = "{'country':?0, 'stats.games':{$gt:4}}")
    List<Player> getAllFromCountryMinGames(String country);

    @Query(value = "{'country':{$regex:?0}, 'stats.games':{$gt:4}}")
    List<Player> getAllMinGames(String regex);
}
