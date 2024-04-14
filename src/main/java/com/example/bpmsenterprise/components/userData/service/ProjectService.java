package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectResponseEntity;
import com.example.bpmsenterprise.components.userData.entity.*;
import com.example.bpmsenterprise.components.userData.interfaces.IProjectControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.ProjectRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_projectRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
class ProjectService implements IProjectControl {
    private final UserData userData;
    private final CompanyRepo companyRepo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ProjectRepo projectRepo;
    private final User_role_in_companyRepo userRoleInCompanyRepo;
    private final User_role_in_projectRepo userRoleInProjectRepo;

    @Override
    public void createNewProject(ProjectResponseEntity projectResponseEntity) {

        User user = userData.getUserByEmail(userData.getCurrentUserEmail());


        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId());
        Company company = companyRepo.findById(userRoleInCompany.getDepartment().getId()).orElseThrow(EntityNotFoundException::new);
        if(userRoleInCompany!=null){

            Project alreadyExist = projectRepo.findByNameInCompany( projectResponseEntity.getName(),company.getName());

            if(alreadyExist!=null)
                throw new NonUniqueResultException("already exist");

            Project project = new Project();
            project.setName(projectResponseEntity.getName());
            project.setDescription(projectResponseEntity.getDescription());
            project.setCreated_at( LocalDate.parse(projectResponseEntity.getStartDate(), formatter));
            project.setDeadline( LocalDate.parse(projectResponseEntity.getFinishDate(), formatter));


            project.setCompany(company);
            Project savedProject = projectRepo.save(project);

            User_role_in_project userRoleInProject = new User_role_in_project();
            userRoleInProject.setUser(user);
            userRoleInProject.setProject(savedProject);
            Role_in_project role_in_project = new Role_in_project();
            role_in_project.setId(1);
            userRoleInProject.setRole_in_project(role_in_project);

            userRoleInProjectRepo.save(userRoleInProject);

        }else{
            throw new EntityNotFoundException("you are not admin");
        }




    }

    @Override
    public void deleteProject(ProjectResponseEntity projectResponseEntity) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        User_role_in_project userRoleInProject = userRoleInProjectRepo.findByNameAndWhereUserAdmin(projectResponseEntity.getName(), user.getId()).orElseThrow(EntityNotFoundException::new);

        projectRepo.delete(userRoleInProject.getProject());
    }


}
