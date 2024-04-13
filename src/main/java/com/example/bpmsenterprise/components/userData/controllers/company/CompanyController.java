package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final ICompanyControl companyControl;

    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> createCompany(@RequestHeader Map<String, String> headers,
                                                @RequestBody CreateCompanyRequest createCompanyRequest) {

            companyControl.create(createCompanyRequest.getName());

            return ResponseEntity.ok("Created!");
    }

    }



