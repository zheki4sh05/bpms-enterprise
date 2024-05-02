package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.controllers.company.CreateCompanyRequest;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Role_in_company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyControl {
    private final UserData userData;
    private final User_role_in_companyRepo userRoleInCompanyRepo;
    private final CompanyRepo companyRepo;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Company createNewCompany(String name) {
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
        return company;
    }

    @Override
    public Company updateCreatedCompany(CreateCompanyRequest createCompanyRequest) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        Company newCompany = new Company();
        newCompany.setName(createCompanyRequest.getName());
        newCompany.setDesc(createCompanyRequest.getDesc());

        User_role_in_company userRoleInCompany = userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId());
        newCompany.setId(userRoleInCompany.getDepartment().getId());

        return companyRepo.save(newCompany);
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

    @Override
    public List<ViewUserAsWorker> getWorkers(String companyName) throws EntityNotFoundException {
        Company company = companyRepo.findBy(companyName).orElseThrow(EntityNotFoundException::new);
        List<ViewUserAsWorker> workers = userRepository.findAllByDepartmentId(company.getId());
        return workers;
    }
}
