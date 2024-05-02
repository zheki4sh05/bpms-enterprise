package com.example.bpmsenterprise.components.authentication.configs.expression;

import com.example.bpmsenterprise.components.authentication.configs.JwtService;
import com.example.bpmsenterprise.components.authentication.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("cse")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final JwtService jwtService;

    public boolean canAccessUser(Map<String, String> headers) {

        User user = getPrincipal();

        Boolean isAccessed = getUserEmail(headers).equals(user.getEmail());

        System.out.println("isAccessed " + isAccessed);

        return isAccessed;
    }
    public User getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return (User) authentication.getPrincipal();
    }
    public String getUserEmail(Map<String, String> headers){
        return jwtService.extractUsername(headers.get("authorization").split(" ")[1]);
    }



}
