package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.TestingConfiguration;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.controllers.company.CreateCompanyRequest;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Import(TestingConfiguration.class)
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @MockBean
    private CompanyRepo companyRepo;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private User_role_in_company userRoleInCompany;
    @MockBean
    private User_role_in_companyRepo userRoleInCompanyRepo;
    @Autowired
    private CompanyService companyService;

    @Test
    public void testCompanyData() {

        String name = "НоваяКомпания";
        Company company = new Company();
        company.setName(name);
        Mockito.when(companyRepo.findBy(name))
                .thenReturn(Optional.of(company));

        Company testCompany = companyService.companyData(name);
        Mockito.verify(companyRepo).findBy(name);
        Assertions.assertEquals(company.getName(), testCompany.getName());

    }

    @Test
    public void testGetAllWorkers() {

        String name = "НоваяКомпания";
        Company company = new Company();
        company.setName(name);
        company.setId(1);

        String[] firstnames = {"1", "2", "3"};
        String[] lastnames = {"1.1", "2.1", "3.1"};
        String[] email = {"1.2", "2.2", "3.2"};
        List<ViewUserAsWorker> workers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            workers.add(new ViewUserAsWorker(firstnames[i], lastnames[i], email[i]));
        }
        Mockito.when(companyRepo.findBy(name)).thenReturn(Optional.of(company)).thenThrow(new EntityNotFoundException());

        Mockito.when(userRepository.findAllByDepartmentId(company.getId()))
                .thenReturn(workers);
        List<ViewUserAsWorker> testWorkers = companyService.getWorkers(name);
        Mockito.verify(userRepository).findAllByDepartmentId(company.getId());
        Assertions.assertEquals(workers, testWorkers);
    }


}