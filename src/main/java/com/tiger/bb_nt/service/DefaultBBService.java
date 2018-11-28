package com.tiger.bb_nt.service;

import com.tiger.bb_nt.model.Country;
import com.tiger.bb_nt.model.Role;
import com.tiger.bb_nt.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DefaultBBService implements BBService {

    private RestTemplate restTemplate=new RestTemplate();
    
    @Override
    public boolean checkIfNTManager(User user, Role role, Country country) {
        ResponseEntity<String> responseJson=
                restTemplate.getForEntity("http://www.buzzerbeater.com/country/"+country.getCode()+"/"
                        +(role.equals(Role.ROLE_U21NT)?"j":"")+"nt/overview.aspx", String.class);
        String managerLogin=responseJson.getBody().split("Manager:")[1].split("</a")[0].split(">")[2];
        return user.getAlias().equals(managerLogin);
    }
}
