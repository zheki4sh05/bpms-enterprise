package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.controllers.user.ProjectAddUserEntity;
import com.example.bpmsenterprise.components.userData.controllers.user.UserInviteResponseEntity;

public interface IProjectHRControl {
    void include(ProjectAddUserEntity userInviteResponseEntity);
}
