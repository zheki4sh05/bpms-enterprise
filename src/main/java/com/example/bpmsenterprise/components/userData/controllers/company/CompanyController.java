package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

        try{
            companyControl.createNewCompany(createCompanyRequest.getName());
            return ResponseEntity.ok(createCompanyRequest.getName());
        }catch (DataIntegrityViolationException e){ // если у пользователя уже есть компания
            return  ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }
    @PutMapping ("/update")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> updateCompanyName(@RequestHeader Map<String, String> headers,
                                                @RequestBody CreateCompanyRequest createCompanyRequest) {

        try{
            companyControl.updateCreatedCompany(createCompanyRequest.getName());
            return ResponseEntity.ok(createCompanyRequest.getName());
        }catch (EntityNotFoundException e){ // если у пользователя уже есть компания
            return  ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }
    @DeleteMapping  ("/delete")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> deleteCompany(@RequestHeader Map<String, String> headers,
                                                    @RequestBody CreateCompanyRequest createCompanyRequest) {

        try{
            companyControl.deleteCreatedCompany(createCompanyRequest.getName());
            return ResponseEntity.ok(createCompanyRequest.getName());
        }catch (EntityNotFoundException e){ // если у пользователя уже есть компания
            return  ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }



    }



