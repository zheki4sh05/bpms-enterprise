package com.example.bpmsenterprise.components.authentication.services;

import com.example.bpmsenterprise.components.authentication.configs.JwtService;
import com.example.bpmsenterprise.components.authentication.configs.expression.CustomSecurityExpression;
import com.example.bpmsenterprise.components.authentication.controllers.Auth.AuthenticationRequest;
import com.example.bpmsenterprise.components.authentication.controllers.Auth.AuthenticationResponse;
import com.example.bpmsenterprise.components.authentication.controllers.Auth.RegisterRequest;
import com.example.bpmsenterprise.components.authentication.entity.Role;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.authentication.token.Token;
import com.example.bpmsenterprise.components.authentication.token.TokenRepository;
import com.example.bpmsenterprise.components.authentication.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserData {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final CustomSecurityExpression customSecurityExpression;
    public AuthenticationResponse register(RegisterRequest request) {
        var user  = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();


        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();


    }
    private void saveUserToken(User user,String jwtToken){
        var token  = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(User user){
        var validTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokens.isEmpty()){
            return;
        }else{
            validTokens.forEach(t->{
                t.setExpired(true);
                t.setRevoked(true);
            });
            tokenRepository.saveAll(validTokens);
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email).get();
    }

    @Override
    public String getCurrentUserEmail() {

        return customSecurityExpression.getPrincipal().getEmail();
    }

    @Override
    public User getCurrentUser() {
        return customSecurityExpression.getPrincipal();
    }


}
