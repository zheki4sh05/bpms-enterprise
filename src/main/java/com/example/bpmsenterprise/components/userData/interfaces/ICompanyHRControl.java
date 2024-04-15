package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.controllers.user.AcceptInvitationEntity;
import com.example.bpmsenterprise.components.userData.controllers.user.UserInviteResponseEntity;
import com.example.bpmsenterprise.components.userData.exceptions.UserWorksInCompanyException;

public interface ICompanyHRControl {
    void invite(UserInviteResponseEntity userInviteResponseEntity) throws UserWorksInCompanyException;

    void acceptInvitation(AcceptInvitationEntity userInviteResponseEntity);
}
