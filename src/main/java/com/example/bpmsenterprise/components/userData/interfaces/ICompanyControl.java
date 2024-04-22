package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.entity.Company;

public interface ICompanyControl {
    void createNewCompany(String name);

    void updateCreatedCompany(String name);

    void deleteCreatedCompany(String name);

    Company companyData(String name);

    UserCompany getUserCompany();
}
