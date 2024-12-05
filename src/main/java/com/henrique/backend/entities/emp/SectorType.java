package com.henrique.backend.entities.emp;

public enum SectorType {
    DEFAULT(0),
    HARDWARE(1), 
    PERIPHELRALS(2),
    ELETRONICS(3),
    SMARTHPHONES(4);

    private int code;

    private SectorType(int code){   
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static SectorType valueOf(int code){
        for(SectorType value: SectorType.values()) {
            if(value.getCode() == code){
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid SectorType code");
    } 

}