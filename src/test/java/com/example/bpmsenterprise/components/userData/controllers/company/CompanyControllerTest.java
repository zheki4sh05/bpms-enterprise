package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {


    @Mock
    ICompanyControl companyControl;

    @InjectMocks
    CompanyController companyController;

    @Test
    void handleGetData_ReturnsValidResponseEntity() {
        var company = new Company(1, "Новая компания", "Описание новой компании");
        doReturn(company).when(this.companyControl).companyData(company.getName());

        CreateCompanyRequest request = new CreateCompanyRequest();
        request.setName(company.getName());

        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", "application/json");

        ResponseEntity<Company> responseEntity = (ResponseEntity<Company>) this.companyController.getCompanyData(headers, request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        // assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());

        assertTrue(responseEntity.getBody().getName().contains(company.getName()));
        assertTrue(responseEntity.getBody().getDesc().contains(company.getDesc()));

    }
}