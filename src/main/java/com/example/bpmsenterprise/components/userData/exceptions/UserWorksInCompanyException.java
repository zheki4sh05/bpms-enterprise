package com.example.bpmsenterprise.components.userData.exceptions;

public class UserWorksInCompanyException extends Exception{
    private Integer code;
    public UserWorksInCompanyException(String message, Integer code){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
