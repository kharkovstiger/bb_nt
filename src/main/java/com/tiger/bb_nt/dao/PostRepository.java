package com.tiger.bb_nt.dao;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findPostsForCountry(Country country);
}
