package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.PostRepository;
import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPostService implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public DefaultPostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getPostsForCountry(Country country) {
        return postRepository.findPostsForCountry(country);
    }
}
