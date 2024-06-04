package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.DTO.AcceptInvDTO;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.UserInviteResponseEntity;
import com.example.bpmsenterprise.components.userData.exceptions.UserWorksInCompanyException;

public interface ICompanyHRControl {
    void invite(UserInviteResponseEntity userInviteResponseEntity) throws UserWorksInCompanyException;

    void acceptInvitation(AcceptInvDTO userInviteResponseEntity);

    UserDTO findUser(String userEmail);

    Integer reject(Integer delId);
}
