package com.example.bpmsenterprise.components.userData.interfaces;


import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.exceptions.SuchEmailIsExistException;

public interface IUserDataControl {
    UserDTO fetch();

    void update(UserDTO userDTO) throws SuchEmailIsExistException;
}
