package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends MongoRepository<User, String> {

    @Override
    @Transactional
    User save(User user);

    @Override
    List<User> findAll();

    User getByLogin(String login);

    @Override
    Optional<User> findById(String id);

    @Override
    boolean existsById(String id);

    @Override
    void deleteById(String id);

    @Query(value = "{'roleCountry':?0}")
    List<User> findByRoleCountry(Country country);
}
