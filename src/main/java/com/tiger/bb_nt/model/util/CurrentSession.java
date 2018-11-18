package com.tiger.bb_nt.model.util;

public class CurrentSession {

    private String country;

    private static CurrentSession ourInstance = new CurrentSession();

    public static CurrentSession getInstance() {
        return ourInstance;
    }

    private CurrentSession() {
    }

    public static void setCountry(String country){
        ourInstance.country=country;
    }

    public static String getCountry(){
        return ourInstance.country;
    }
}
