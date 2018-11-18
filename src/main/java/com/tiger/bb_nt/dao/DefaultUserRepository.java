package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultUserRepository implements UserRepository {

    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DefaultUserRepository(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public void delete(String id) {
        crudUserRepository.deleteById(id);
    }

    @Override
    public User findOne(String id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByLogin(String login) {
        return crudUserRepository.getByLogin(login);
    }

    @Override
    public List<User> findAll() {
        return crudUserRepository.findAll();
    }
}
