package com.tiger.bb_nt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Data
@Document
@EqualsAndHashCode(of = "id")
public class User implements Serializable {
    
    @Id
    private String id;
    private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;
    private String phone;
    private String alias;
    private String team;
    private String country;
    
    @Email
    private String email;

    private Set<Role> roles;
    private Country roleCountry;
    private boolean enabled = true;

    public boolean hasRole(Role role) {
        return roles.contains(role);
    }

    public void deleteRole(Role role) {
        roles.remove(role);
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
