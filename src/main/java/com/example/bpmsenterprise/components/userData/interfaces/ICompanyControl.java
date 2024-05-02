package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.controllers.company.CreateCompanyRequest;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;

import java.util.List;

public interface ICompanyControl {
    Company createNewCompany(String name);

    Company updateCreatedCompany(CreateCompanyRequest companyUpdate);

    void deleteCreatedCompany(String name);

    Company companyData(String name);

    UserCompany getUserCompany();

    List<ViewUserAsWorker> getWorkers(String companyName);
}
