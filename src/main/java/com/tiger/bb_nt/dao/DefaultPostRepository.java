package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DefaultPostRepository implements PostRepository {

    private final CrudPostRepository crudPostRepository;

    @Autowired
    public DefaultPostRepository(CrudPostRepository crudPostRepository) {
        this.crudPostRepository = crudPostRepository;
    }

    @Override
    public List<Post> findPostsForCountry(Country country) {
        return crudPostRepository.findPostsByCountry(country);
    }
}
