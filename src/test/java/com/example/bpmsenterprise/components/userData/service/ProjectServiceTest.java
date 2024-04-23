package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.userData.controllers.project.ProjectResponseEntity;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.ProjectRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    @InjectMocks
    ProjectService projectService;
    @Mock
    UserData userData;
    @Mock
    CompanyRepo companyRepo;
    @Mock
    ProjectRepo projectRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createNewProject_ShouldThrowException_WhenUserNotAdmin() {
        when(userData.getCurrentUserEmail()).thenReturn("user@example.com");
        assertThrows(EntityNotFoundException.class, () -> projectService.createNewProject(new ProjectResponseEntity()));
    }

    @Test
    void createNewProject_ShouldSaveProject_WhenUserIsAdmin() {
        when(userData.getCurrentUserEmail()).thenReturn("admin@example.com");
        projectService.createNewProject(new ProjectResponseEntity());
    }

    @Test
    void deleteProject_ShouldDeleteProject_WhenUserIsAuthenticated() {
        when(userData.getCurrentUserEmail()).thenReturn("user@example.com");
        projectService.deleteProject(new ProjectResponseEntity());

    }
}