package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.ProjectAddUserEntity;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_project;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_projectRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;

class ProjectHRServiceTest {
    @InjectMocks
    private ProjectHRService projectHRService;
    @Mock
    private User_role_in_projectRepo userRoleInProjectRepo;
    @Mock
    private User_role_in_companyRepo userRoleInCompanyRepo;
    @Mock
    private UserData userData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInclude_whenUserRoleInCompanyExists_shouldSaveUserRoleInProject() {
        ProjectAddUserEntity projectAddUserEntity = new ProjectAddUserEntity();
        projectAddUserEntity.setEmail("test@example.com");
        projectAddUserEntity.setCompanyName("Test Company");
        projectAddUserEntity.setProjectName("Test Project");
        User includingUser = new User();
        when(userData.getUserByEmail(anyString())).thenReturn(includingUser);
        User_role_in_company includedUser = new User_role_in_company();
        when(userRoleInCompanyRepo.findByUserEmailAndUserIsParticipant(anyString(), anyString())).thenReturn(includedUser);
        User_role_in_project adminRole = new User_role_in_project();
        when(userRoleInProjectRepo.findByNameAndWhereUserAdmin(anyString(), Math.toIntExact(anyLong()))).thenReturn(java.util.Optional.of(adminRole));
        projectHRService.include(projectAddUserEntity);
        verify(userRoleInProjectRepo).save(any(User_role_in_project.class));
    }

    @Test
    void testInclude_whenUserRoleInCompanyDoesNotExist_shouldNotSaveUserRoleInProject() {
        ProjectAddUserEntity projectAddUserEntity = new ProjectAddUserEntity();
        projectAddUserEntity.setEmail("test@example.com");
        projectAddUserEntity.setCompanyName("Test Company");
        projectAddUserEntity.setProjectName("Test Project");
        User includingUser = new User();
        when(userData.getUserByEmail(anyString())).thenReturn(includingUser);
        when(userRoleInCompanyRepo.findByUserEmailAndUserIsParticipant(anyString(), anyString())).thenReturn(null);
        projectHRService.include(projectAddUserEntity);
        verify(userRoleInProjectRepo, never()).save(any(User_role_in_project.class));
    }
}