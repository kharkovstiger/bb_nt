package com.tiger.bb_nt.model.util;

import com.tiger.bb_nt.model.Country;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
//@Scope("session")
@Data
public class CurrentSession {

    private Country country;    
}
