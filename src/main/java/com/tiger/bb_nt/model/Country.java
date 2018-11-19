package com.tiger.bb_nt.model;

public enum Country {
    Ukraina(33),
    Rossiya(19);
    
    public int code;
    
    public int getCode(){
        return code;
    }

    private Country(int code) {
        this.code = code;
    }
}
