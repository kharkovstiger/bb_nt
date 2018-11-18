package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.UserRepository;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;
import com.tiger.bb_nt.util.XMLUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.security.auth.login.LoginException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
public class DefaultUserService implements UserService {
    
    private final UserRepository userRepository;
    private final BBAPIService bbapiService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, BBAPIService bbapiService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bbapiService = bbapiService;
        this.passwordEncoder = passwordEncoder;
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
        user.setCode(passwordEncoder.encode(authenticationRequest.getCode()));
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
}
