package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

@SpringBootTest
class CompanyServiceTest {
    @InjectMocks
    private CompanyService companyService;
    @Mock
    private UserData userData;
    @Mock
    private User_role_in_companyRepo userRoleInCompanyRepo;
    @Mock
    private CompanyRepo companyRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewCompany() {
        String companyName = "TestCompany";
        User user = new User();
        when(userData.getCurrentUserEmail()).thenReturn("test@example.com");
        when(userData.getUserByEmail("test@example.com")).thenReturn(user);
        companyService.createNewCompany(companyName);
        verify(companyRepo).save(Mockito.any(Company.class));
    }

    @Test
    void testUpdateCreatedCompany() {
        String updatedCompanyName = "UpdatedCompany";
        User user = new User();
        when(userData.getCurrentUserEmail()).thenReturn("test@example.com");
        when(userData.getUserByEmail("test@example.com")).thenReturn(user);
        User_role_in_company userRoleInCompany = new User_role_in_company();
        userRoleInCompany.setDepartment(new Company());
        when(userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId())).thenReturn(userRoleInCompany);
        companyService.updateCreatedCompany(updatedCompanyName);
        verify(companyRepo).save(Mockito.any(Company.class));
    }

    @Test
    void testDeleteCreatedCompany() {
        String companyName = "TestCompany";
        User user = new User();
        when(userData.getCurrentUserEmail()).thenReturn("test@example.com");
        when(userData.getUserByEmail("test@example.com")).thenReturn(user);
        Company company = new Company();
        when(companyRepo.findBy(companyName)).thenReturn(Optional.of(company));
        User_role_in_company userRoleInCompany = new User_role_in_company();
        when(userRoleInCompanyRepo.findByUserIdAndCompanyIdAndWhereUserAdmin(user.getId(), company.getId())).thenReturn(Optional.of(userRoleInCompany));
        companyService.deleteCreatedCompany(companyName);
        verify(companyRepo).delete(company);
    }
}