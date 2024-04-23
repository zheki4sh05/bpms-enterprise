package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Role_in_company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CompanyService implements ICompanyControl {
    private final UserData userData;
    private final User_role_in_companyRepo userRoleInCompanyRepo;
    private final CompanyRepo companyRepo;
    @Override
    @Transactional
    public void createNewCompany(String name) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        Company company = new Company();
        company.setName(name);
        company = companyRepo.save(company);
        User_role_in_company userRoleInCompany = new User_role_in_company();
        userRoleInCompany.setUser(user);
        userRoleInCompany.setDepartment(company);
        Role_in_company role_in_company = new Role_in_company();
        role_in_company.setName("admin");
        role_in_company.setId(1);
        userRoleInCompany.setRole_in_company(role_in_company);
        User_role_in_company user_role_in_company = userRoleInCompanyRepo.save(userRoleInCompany);
   }
    @Override
    public void updateCreatedCompany(String name) {
        Company newCompany = new Company();
        newCompany.setName(name);
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId());
        newCompany.setId(userRoleInCompany.getDepartment().getId());
        companyRepo.save(newCompany);
    }
    @Override
    public void deleteCreatedCompany(String name) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        Company company =  companyRepo.findBy(name).orElseThrow(EntityNotFoundException::new);
       User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserIdAndCompanyIdAndWhereUserAdmin(user.getId(), company.getId()).orElseThrow(EntityNotFoundException::new);
       companyRepo.delete(company);
    }
    @Override
    public Company companyData(String name) {
        return companyRepo.findBy(name).orElseThrow(EntityNotFoundException::new);
    }
    @Override
    public UserCompany getUserCompany() {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserId(user.getId()).orElseThrow(EntityNotFoundException::new);
        UserCompany userCompany = new UserCompany();
        userCompany.setName(userRoleInCompany.getDepartment().getName());
        userCompany.setDesc(userRoleInCompany.getDepartment().getDesc());
        userCompany.setRole(userRoleInCompany.getRole_in_company().getName());
        return userCompany;
    }
}
