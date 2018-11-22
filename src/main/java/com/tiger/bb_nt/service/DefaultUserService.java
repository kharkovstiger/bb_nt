package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.UserRepository;
import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.AuthorizedUser;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;
import com.tiger.bb_nt.util.XMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.Set;

@Service
public class DefaultUserService implements UserService {
    
    private final UserRepository userRepository;
    private final BBAPIService bbapiService;
    private final PasswordEncoder passwordEncoder;
    private final BBService bbService;

    @Autowired
    public DefaultUserService(UserRepository userRepository, BBAPIService bbapiService, PasswordEncoder passwordEncoder, BBService bbService) {
        this.userRepository = userRepository;
        this.bbapiService = bbapiService;
        this.passwordEncoder = passwordEncoder;
        this.bbService = bbService;
    }

    @Override
    public User getByLogin(String loginLowerCase) {
        return userRepository.getByLogin(loginLowerCase);
    }

    @Override
    public User createUser(JwtAuthenticationRequest authenticationRequest) throws LoginException {
        String json=bbapiService.login(authenticationRequest.getLogin(), authenticationRequest.getCode(),1).getBody();
        Document doc= XMLUtils.getDocument(json);
        if (doc.getElementsByTagName("team").item(0)==null){
            throw new LoginException();
        }
        
        User user=new User();
        user.setLogin(authenticationRequest.getLogin());
        //I can't encode the "password", i need it
        user.setCode(authenticationRequest.getCode());
        user.setAlias(doc.getElementsByTagName("owner").item(0).getTextContent().trim());
        user.setId(doc.getElementsByTagName("team").item(0).getAttributes().getNamedItem("id").getNodeValue());
        Set<Role> roles=new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        user.setTeam(doc.getElementsByTagName("teamName").item(0).getTextContent());
        user.setCountry(doc.getElementsByTagName("country").item(0).getTextContent());
        
        user=userRepository.save(user);
        return user;
    }

    @Override
    public User getCurrentUser() {
        return AuthorizedUser.get().getUser();
    }

    @Override
    public void afterLogin() {
        User user=getCurrentUser();
//        if (user.getCountry() != null) {
//            session.setCountry(Country.valueOf(user.getCountry()));
//        }
    }

    @Override
    public boolean tryTologin(String login, String code) {
        String json=bbapiService.login(login, code,1).getBody();
        Document doc= XMLUtils.getDocument(json);
        if (doc.getElementsByTagName("team").item(0)==null){
            return false;
        }
        return true;
    }

    @Override
    public User updateUser(User currentUser) {
        return userRepository.save(currentUser);
    }

    @Override
    public User getUser(String userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User changeRole(String userId, Role role) {
        User user=userRepository.findOne(userId);
        if (user != null){
            if (user.hasRole(role)){
                user.deleteRole(role);
            } else {
                user.addRole(role);
            }
            userRepository.save(user);
        }
        return user;
    }

    @Override
    public User processRequestToChangeRole(Role role, Country country) {
        User user=getCurrentUser();
        boolean nt=bbService.checkIfNTManager(user, role, country);
        if (nt){
            user.addRole(role);
            userRepository.save(user);
        }
        return user;
    }
}
