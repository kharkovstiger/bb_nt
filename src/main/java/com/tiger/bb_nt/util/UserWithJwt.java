package com.tiger.bb_nt.util;

import com.tiger.bb_nt.model.User;

public class UserWithJwt {
    private String accessToken;
    private User user;

    public UserWithJwt(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
