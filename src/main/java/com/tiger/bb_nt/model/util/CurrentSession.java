package com.tiger.bb_nt.model.util;

import com.tiger.bb_nt.model.Country;

public class CurrentSession {

    private Country country;
    
    private static CurrentSession ourInstance = new CurrentSession();
    
    public static CurrentSession getInstance() {
        return ourInstance;
    }
    
    private CurrentSession() {
    }
    
    public static void setCountry(Country country){
        ourInstance.country=country;
    }
    
    public static Country getCountry(){
        return ourInstance.country;
    }
    
}
