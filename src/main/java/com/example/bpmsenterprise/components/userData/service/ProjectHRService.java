package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.ProjectAddUserEntity;
import com.example.bpmsenterprise.components.userData.entity.Role_in_project;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_project;
import com.example.bpmsenterprise.components.userData.interfaces.IProjectHRControl;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_projectRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProjectHRService implements IProjectHRControl {

    private final User_role_in_projectRepo userRoleInProjectRepo;
    private final User_role_in_companyRepo userRoleInCompanyRepo;
    private final UserData userData;

    @Override
    public void include(ProjectAddUserEntity projectAddUserEntity) {

        User includingUser = userData.getUserByEmail(userData.getCurrentUserEmail());
        if (userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(includingUser.getId()) != null) {
            User_role_in_company includedUser = userRoleInCompanyRepo.findByUserEmailAndUserIsParticipant(
                    projectAddUserEntity.getEmail(),
                    projectAddUserEntity.getCompanyName());
            if (includedUser != null) {
                User_role_in_project adminRole = userRoleInProjectRepo.findByNameAndWhereUserAdmin(projectAddUserEntity.getProjectName(), includingUser.getId()).orElseThrow(EntityNotFoundException::new);
                User_role_in_project userRoleInProject = new User_role_in_project();
                userRoleInProject.setProject(adminRole.getProject());
                userRoleInProject.setUser(includedUser.getUser());
                Role_in_project role_in_project = new Role_in_project();
                role_in_project.setId(2);
                role_in_project.setName("participant");
                userRoleInProject.setRole_in_project(role_in_project);

                userRoleInProjectRepo.save(userRoleInProject);
            }

        }


    }
}
