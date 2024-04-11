package com.example.bpmsenterprise.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class MainAuthController {
    @GetMapping("/unsecured")
    public String unsecuredData(){
        return "unsecuredData";
    }
    @GetMapping("/secured")
    public String securedData(){
        return "securedData";
    }
    @GetMapping("/info")
    public String useData(Principal principal){
        return principal.getName();
    }

}
