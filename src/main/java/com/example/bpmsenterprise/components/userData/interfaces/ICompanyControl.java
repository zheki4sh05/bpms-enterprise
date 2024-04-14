package com.example.bpmsenterprise.components.userData.interfaces;

public interface ICompanyControl {
    void createNewCompany(String name);

    void updateCreatedCompany(String name);

    void deleteCreatedCompany(String name);

    void inviteUserToCompany(String userEmail);

    void deleteUserFromCompany(String userEmail);
}
