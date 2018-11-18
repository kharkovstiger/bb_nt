package com.tiger.bb_nt.service;

import com.tiger.bb_nt.dao.UserRepository;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import com.tiger.bb_nt.security.jwt.JwtAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

    @Autowired
    public DefaultUserService(UserRepository userRepository, BBAPIService bbapiService) {
        this.userRepository = userRepository;
        this.bbapiService = bbapiService;
    }

    @Override
    public User getByLogin(String loginLowerCase) {
        return userRepository.getByLogin(loginLowerCase);
    }

    @Override
    public User createUser(JwtAuthenticationRequest authenticationRequest) {
        String json=bbapiService.login(authenticationRequest.getLogin(), authenticationRequest.getCode(),1).getBody();
        //TODO check that login was successful
        Document doc=getDocument(json);
        NodeList nodeList=doc.getElementsByTagName("team");
        
        User user=new User();
        user.setLogin(authenticationRequest.getLogin());
        user.setCode(authenticationRequest.getCode());
        user.setAlias(doc.getElementsByTagName("owner").item(0).getTextContent().trim());
        user.setId(doc.getAttributes().getNamedItem("id").getTextContent().trim());
        Set<Role> roles=new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        user.setTeam(doc.getElementsByTagName("teamName").item(0).getTextContent());
        user.setCountry(doc.getElementsByTagName("country").item(0).getTextContent());
        
        user=userRepository.save(user);
        return user;
    }

    private Document getDocument(String s){
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(new InputSource(new StringReader(s)));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
