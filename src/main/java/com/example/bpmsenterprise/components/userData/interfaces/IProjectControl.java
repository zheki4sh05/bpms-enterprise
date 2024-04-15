package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.controllers.project.ProjectResponseEntity;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectUpdateResponseEntity;

public interface IProjectControl {

    void createNewProject(ProjectResponseEntity projectResponseEntity);

    void deleteProject(ProjectResponseEntity projectResponseEntity);

    void updateProject(ProjectUpdateResponseEntity projectResponseEntity);
}
