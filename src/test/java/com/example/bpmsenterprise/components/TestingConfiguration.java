package com.example.bpmsenterprise.components;

import com.example.bpmsenterprise.components.authentication.configs.JwtService;
import com.example.bpmsenterprise.components.authentication.configs.expression.CustomSecurityExpression;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.authentication.token.TokenRepository;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import com.example.bpmsenterprise.components.userData.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestingConfiguration {


//    @Bean
//    public UserRepository userRepository() {
//        return Mockito.mock(UserRepository.class);
//    }

    //    @Bean
//    @Primary
//    public CompanyService companyService( UserData userData,
//                                          User_role_in_companyRepo userRoleInCompanyRepo,
//
//                                        CompanyRepo companyRepo) {
//        return new CompanyService(userData, userRoleInCompanyRepo, companyRepo, userRepository());
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return Mockito.mock(PasswordEncoder.class);
//    }

//    @Bean
//    public JwtService jwtService() {
//        return new JwtService();
//    }

//    @Bean
//    @Primary
//    public AuthenticationManager authenticationManager() {
//        return Mockito.mock(AuthenticationManager.class);
//    }

//    @Bean
//    public TokenRepository tokenRepository() {
//        return Mockito.mock(TokenRepository.class);
//    }
//
//    @Bean
//    public CustomSecurityExpression customSecurityExpression(JwtService jwtService) {
//        return new CustomSecurityExpression(jwtService);
//    }
//

}
