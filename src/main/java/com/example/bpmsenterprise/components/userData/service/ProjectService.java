package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.DTO.CreateProject.CreateProjectDTO;
import com.example.bpmsenterprise.components.userData.DTO.ProjectStatusDTO;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectResponseEntity;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectUpdateResponseEntity;
import com.example.bpmsenterprise.components.userData.entity.*;
import com.example.bpmsenterprise.components.userData.entity.views.ViewProject;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
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
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@RequiredArgsConstructor
class ProjectService implements IProjectControl {
    private final UserData userData;
    private final CompanyRepo companyRepo;
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final ProjectRepo projectRepo;
    private final User_role_in_companyRepo userRoleInCompanyRepo;
    private final User_role_in_projectRepo userRoleInProjectRepo;

    @Override
    public Integer createNewProject(CreateProjectDTO projectResponseEntity) {
        Integer id;
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId());
        Company company = companyRepo.findById(userRoleInCompany.getDepartment().getId()).orElseThrow(EntityNotFoundException::new);
        if (userRoleInCompany != null) {

            Project alreadyExist = projectRepo.findByNameInCompany(projectResponseEntity.getName(), company.getName());

            if (alreadyExist != null)
                throw new NonUniqueResultException("already exist");

//---сохраняем сущность проекта
            Project project = projectMapper(projectResponseEntity, company);
            Project savedProject = projectRepo.save(project);
            id = savedProject.getId();
//            viewProject.setId(savedProject.getId());
//            viewProject.setName(savedProject.getName());
//            viewProject.setDescription(savedProject.getDescription());
//            viewProject.setColor(savedProject.getColor());
//            viewProject.setCreatedAt(savedProject.getCreated_at());
//            viewProject.setDeadline(savedProject.getDeadline());
//            viewProject.setRole("Управляющий");
//---------------
            //---Назначаем роли пользователям (добавляем в проект)
            projectResponseEntity.getLeader().forEach(l -> {
                User_role_in_project userRoleInProject = new User_role_in_project();
                User savedUser = userRepository.findByEmail(l.getEmail()).orElseThrow(EntityNotFoundException::new);
                userRoleInProject.setUser(savedUser);
                userRoleInProject.setProject(savedProject);
                Role_in_project role_in_project = new Role_in_project();
                role_in_project.setId(1);
                userRoleInProject.setRole_in_project(role_in_project);
                userRoleInProjectRepo.save(userRoleInProject);
            });

            projectResponseEntity.getWorkers().forEach(w -> {
                User_role_in_project userRoleInProject = new User_role_in_project();
                User savedUser = userRepository.findByEmail(w.getEmail()).orElseThrow(EntityNotFoundException::new);
                userRoleInProject.setUser(savedUser);
                userRoleInProject.setProject(savedProject);
                Role_in_project role_in_project = new Role_in_project();
                role_in_project.setId(2);
                userRoleInProject.setRole_in_project(role_in_project);
                userRoleInProjectRepo.save(userRoleInProject);
            });

//--------------
        }else{
            throw new EntityNotFoundException("you are not admin");
        }
        return id;
    }

    @Override
    public void deleteProject(ProjectResponseEntity projectResponseEntity) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        User_role_in_project userRoleInProject = userRoleInProjectRepo.findByNameAndWhereUserAdmin(projectResponseEntity.getName(), user.getId()).orElseThrow(EntityNotFoundException::new);

        projectRepo.delete(userRoleInProject.getProject());
    }

    @Override
    public void updateProject(ProjectUpdateResponseEntity projectResponseEntity) {
//        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
//        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId());
//        Company company = userRoleInCompany.getDepartment();
//        User_role_in_project userRoleInProject = userRoleInProjectRepo.findByNameAndWhereUserAdmin(projectResponseEntity.getOldName(), user.getId()).orElseThrow(EntityNotFoundException::new);
//        Project oldProject = userRoleInProject.getProject();
//        Project updatedProject =  projectMapper(projectResponseEntity,company);
//        updatedProject.setId(oldProject.getId());
//        projectRepo.save(updatedProject);

    }

    @Override
    public List<ViewProject> getAllProjects(String companyName) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserId(user.getId()).orElseThrow(EntityNotFoundException::new);
        List<ViewProject> projects;
        if (userRoleInCompany.getRole_in_company().getName().equals("admin")) {

            projects = projectRepo.getAllByDepNameAndUserId(companyName).stream().peek((item) -> item.setRole("admin")).toList();

        } else {
            projects = projectRepo.getByDepartmentNameAndUserId(user.getId(), companyName).orElseThrow(EntityNotFoundException::new);
        }


        return projects.stream().peek((item) -> item.setRoleName(getRoleName(item.getRole()))).toList();
    }

    private String getRoleName(String type) {
        switch (type) {
            case "admin": {
                return "Управляющий";
            }
            case "participant": {
                return "Участник";
            }
            case "viewer": {
                return "Наблюдаю";
            }
            default: {
                return "Участник";
            }
        }
    }

    @Override
    public List<ViewUserAsWorker> getAllWorkers(Integer projectId) {

        List<ViewUserAsWorker> list = userRepository.findAllByProjectId(projectId).orElseThrow(EntityNotFoundException::new);

        return list;
    }

    @Override
    public Double projectsResult(Integer projectId) {

        Double result = projectRepo.countResultById(projectId).orElseThrow(EntityNotFoundException::new);

        return result;
    }

    @Override
    public Integer projectsOverdue(Integer projectId) {

        Integer status = projectRepo.isOverdue(projectId);

        return status;
    }

    @Override
    public List<ProjectStatusDTO> getProjectsStatuses(String companyName) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserId(user.getId()).orElseThrow(EntityNotFoundException::new);
        List<ProjectStatusDTO> list;
        if (userRoleInCompany.getRole_in_company().getName().equals("admin")) {
            list = projectRepo.findAllByDepartmentName(companyName);

        } else {
            list = projectRepo.findByDepartmentNameAndUserId(user.getId(), companyName).orElseThrow(EntityNotFoundException::new);


        }

        for (ProjectStatusDTO item : list) {
            item.setDone(projectRepo.countResultById(item.getId()).orElse(0.));
            item.setIsOverdue(projectRepo.isOverdue(item.getId()));
            item.setWorkers(getAllWorkers(item.getId()).stream().map(ViewUserAsWorker::getId).toList());
            item.setIsActive(isProjectActive(item.getId()));
            item.setLeader(userRepository.getLeaderById(item.getId()).getId());
        }


        return list;
    }

    @Override
    public Integer isProjectActive(Integer id) {
        Integer count = projectRepo.findAssignmentsByProjectId(id);
        return count > 0 ? 1 : 0;
    }

    private Project projectMapper(CreateProjectDTO projectResponseEntity, Company company) {
        Project project = new Project();
        project.setCompany(company);
        project.setName(projectResponseEntity.getName());
        project.setDescription(projectResponseEntity.getDesc());
        project.setCreated_at(LocalDate.parse(projectResponseEntity.getStartDate(), formatter));
        project.setDeadline(LocalDate.parse(projectResponseEntity.getFinishDate(), formatter));
        project.setColor(projectResponseEntity.getColor());
        return project;
    }




}
