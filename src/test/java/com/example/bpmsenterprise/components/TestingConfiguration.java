package com.example.bpmsenterprise.components;

import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import com.example.bpmsenterprise.components.userData.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@RequiredArgsConstructor
public class TestingConfiguration {

    @Bean
    @Primary
    public CompanyService companyService(final UserData userData,
                                         final User_role_in_companyRepo userRoleInCompanyRepo,
                                         final UserRepository userRepository,
                                         final CompanyRepo companyRepo
    ) {
        return new CompanyService(userData, userRoleInCompanyRepo, companyRepo, userRepository);
    }
}
