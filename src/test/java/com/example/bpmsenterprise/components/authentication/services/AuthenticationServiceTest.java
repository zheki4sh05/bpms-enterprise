package com.example.bpmsenterprise.components.authentication.services;

import com.example.bpmsenterprise.components.TestingConfiguration;
import com.example.bpmsenterprise.components.authentication.configs.JwtService;
import com.example.bpmsenterprise.components.authentication.configs.expression.CustomSecurityExpression;
import com.example.bpmsenterprise.components.authentication.controllers.Auth.AuthenticationRequest;
import com.example.bpmsenterprise.components.authentication.controllers.Auth.AuthenticationResponse;
import com.example.bpmsenterprise.components.authentication.entity.Role;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.authentication.token.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@Import(TestingConfiguration.class)
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {


    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private TokenRepository tokenRepository;
    @MockBean
    private CustomSecurityExpression customSecurityExpression;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void login() {
        Integer userId = 1;
        String username = "username@mail.ru";
        String password = "123456789";

        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InVzZXJuYW1lQG1haWwucnUiLCJwYXNzd29yZCI6MTIzNDU2Nzg5fQ.8MHFZZ3yClm5Yby52oV5bRggaTdjxUeFLDE4xsKLrTQ";

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail(username);
        request.setPassword(password);
        User user = new User();
        user.setId(userId);
        user.setFirstname(username);
        user.setRole(Role.USER);

        Mockito.when(userRepository.findByEmail(username))
                .thenReturn(Optional.of(user));
        Mockito.when(jwtService.generateToken(user))
                .thenReturn(accessToken);

        AuthenticationResponse response = authenticationService.authenticate(request);
//        Mockito.verify(authenticationManager)
//                .authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                username,
//                               password)
//                );

        Assertions.assertEquals(response.getToken(), accessToken);
        Assertions.assertNotNull(response.getToken());
    }


}