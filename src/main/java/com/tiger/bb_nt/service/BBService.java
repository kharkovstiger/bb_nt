package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;

public interface BBService {
    boolean checkIfNTManager(User user, Role role, Country country);
}
