package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    List<Post> getPostsForCountry(Country country);
}
