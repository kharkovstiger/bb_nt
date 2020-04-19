package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CrudPostRepository extends MongoRepository<Post, String> {

    @Override
    Post save(Post post);

    @Override
    List<Post> findAll();

    @Override
    Optional<Post> findById(String id);

    @Override
    void deleteById(String id);

    @Query(value = "{'country':?0}")
    List<Post> findPostsByCountry(Country country);
}
