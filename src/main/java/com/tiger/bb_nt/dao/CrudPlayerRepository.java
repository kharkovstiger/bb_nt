package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.bb.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

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

    @Query(value = "{'nationality':?0}")
    List<Player> findPlayersForNT(Country roleCountry);

    @Query(value = "{'nationality':?0, 'age':{$lt: 22}}")
    List<Player> findPlayersForU21NT(Country roleCountry);

    @Override
    boolean existsById(String id);
}
