package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "Company controller")
@CrossOrigin
@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final ICompanyControl companyControl;

    @CrossOrigin
    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> createCompany(@RequestHeader Map<String, String> headers,
                                                @RequestBody CreateCompanyRequest createCompanyRequest) {

        try{
            Company createdCompany = companyControl.createNewCompany(createCompanyRequest.getName());
            return ResponseEntity.ok(createdCompany.getName());
        }catch (DataIntegrityViolationException e){ // если у пользователя уже есть компания
            return  ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @PutMapping("/update")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> updateCompanyName(@RequestHeader Map<String, String> headers,
                                               @RequestBody CreateCompanyRequest createCompanyRequest) {

        try { // доделать возврат обновленных данных на клиент
            Company company = companyControl.updateCreatedCompany(createCompanyRequest);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (EntityNotFoundException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }

    @DeleteMapping  ("/delete")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> deleteCompany(@RequestHeader Map<String, String> headers,
                                                @RequestBody CreateCompanyRequest createCompanyRequest) {

        try {
            companyControl.deleteCreatedCompany(createCompanyRequest.getName());
            return ResponseEntity.ok(createCompanyRequest.getName());
        } catch (EntityNotFoundException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }

    @GetMapping("/")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getCompanyData(@RequestHeader Map<String, String> headers,
                                            @RequestBody CreateCompanyRequest createCompanyRequest) {

        try {
            Company company = companyControl.companyData(createCompanyRequest.getName());
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a company");
        }

    }

    @GetMapping("/userCompany")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getUserCompanyData(@RequestHeader Map<String, String> headers) {
        try {
            UserCompany userCompany = companyControl.getUserCompany();
            return new ResponseEntity<>(userCompany, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND.value()), e),
                    HttpStatus.NOT_FOUND);
        }


    }


}



