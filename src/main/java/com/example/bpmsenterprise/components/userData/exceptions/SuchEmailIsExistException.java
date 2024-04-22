package com.example.bpmsenterprise.components.userData.exceptions;

public class SuchEmailIsExistException extends Exception {
    private Integer code;

    public SuchEmailIsExistException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
