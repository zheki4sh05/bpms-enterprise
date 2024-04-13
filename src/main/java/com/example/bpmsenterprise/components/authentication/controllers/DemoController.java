package com.example.bpmsenterprise.components.authentication.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {

    @GetMapping
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> sayHello(@RequestHeader Map<String, String> headers){


        System.out.println("HEllow!");

        return ResponseEntity.ok("Hellow from controller");
    }
}
