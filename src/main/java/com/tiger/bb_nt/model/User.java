package com.tiger.bb_nt.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Set;

@Data
@Document
@EqualsAndHashCode(of = "login")
public class User implements Serializable {
    
    @Id
    private String login;
    private String code;
    private String phone;
    private String alias;
    private String team;
    
    @Email
    private String email;

    private Set<Role> roles;
    private boolean enabled = true;
}