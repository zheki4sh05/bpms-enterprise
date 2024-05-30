package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.DTO.CreateProject.CreateProjectDTO;
import com.example.bpmsenterprise.components.userData.DTO.ProjectStatusDTO;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectResponseEntity;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectUpdateResponseEntity;
import com.example.bpmsenterprise.components.userData.entity.views.ViewProject;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;

import java.util.List;


public interface IProjectControl {

    Integer createNewProject(CreateProjectDTO projectResponseEntity);

    void deleteProject(ProjectResponseEntity projectResponseEntity);

    void updateProject(ProjectUpdateResponseEntity projectResponseEntity);


    List<ViewProject> getAllProjects(String companyName);

    List<ViewUserAsWorker> getAllWorkers(Integer projectName);


    Double projectsResult(Integer projectId);

    Integer projectsOverdue(Integer projectId);

    List<ProjectStatusDTO> getProjectsStatuses(String companyName);

    Integer isProjectActive(Integer id);
}
