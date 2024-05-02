package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.DTO.CreateProject.CreateProjectDTO;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectResponseEntity;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectUpdateResponseEntity;
import com.example.bpmsenterprise.components.userData.entity.views.ViewProject;

import java.util.List;


public interface IProjectControl {

    void createNewProject(CreateProjectDTO projectResponseEntity);

    void deleteProject(ProjectResponseEntity projectResponseEntity);

    void updateProject(ProjectUpdateResponseEntity projectResponseEntity);


    List<ViewProject> getAllProjects(String companyName);
}
