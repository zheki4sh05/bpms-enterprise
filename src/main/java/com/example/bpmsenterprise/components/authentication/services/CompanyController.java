//package com.example.bpmsenterprise.layers.services;
//
//import com.example.bpmsenterprise.controllers.Auth.AuthenticationResponse;
//import com.example.bpmsenterprise.controllers.Auth.RegisterRequest;
//import com.example.bpmsenterprise.controllers.Company.CompanyService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("company")
//@AllArgsConstructor
//public class CompanyController {
//    private final CompanyService companyService;
//    @PostMapping("create")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request){
//        return ResponseEntity.ok(companyService.create(request));
//    }
//}
