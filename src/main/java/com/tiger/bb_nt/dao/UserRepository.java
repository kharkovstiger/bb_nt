package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    // false if not found
    void delete(String id);

    // null if not found
    User findOne(String id);

    // null if not found
    User getByLogin(String login);

    List<User> findAll();
}
