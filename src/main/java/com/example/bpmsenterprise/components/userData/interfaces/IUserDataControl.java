package com.example.bpmsenterprise.components.userData.interfaces;


import com.example.bpmsenterprise.components.userData.DTO.NotificationDTO;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.exceptions.SuchEmailIsExistException;

import java.util.List;

public interface IUserDataControl {
    UserDTO fetch();

    void update(UserDTO userDTO) throws SuchEmailIsExistException;

    List<NotificationDTO> getNotifByEmail(String email);
}
