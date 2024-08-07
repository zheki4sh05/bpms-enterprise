package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.DTO.AcceptInvDTO;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.UserInviteResponseEntity;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Invitation;
import com.example.bpmsenterprise.components.userData.entity.Role_in_company;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.exceptions.UserWorksInCompanyException;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyHRControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.InvitationsRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CompanyHRService implements ICompanyHRControl {
    private final UserData userData;
    private final UserRepository userRepository;
    private final InvitationsRepo invitationsRepo;
    private final User_role_in_companyRepo userRoleInCompanyRepo;
    private final CompanyRepo companyRepo;

    @Override
    public void invite(UserInviteResponseEntity userInviteResponseEntity) throws UserWorksInCompanyException {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        if(userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(user.getId())!=null){

            User invitedUser = userRepository.findByEmail(userInviteResponseEntity.getEmail()).orElseThrow(EntityNotFoundException::new);

            if (userRoleInCompanyRepo.findFreeUserByEmail(invitedUser.getEmail()) == null) {
                //создать приглашение

                Invitation invitation = new Invitation();
                invitation.setCompany(companyRepo.findBy(userInviteResponseEntity.getCompanyName()).orElseThrow());
                invitation.setUser(invitedUser);
                invitation.setMessage(userInviteResponseEntity.getMessage());
                invitation.setDate(LocalDate.now());
                invitationsRepo.save(invitation);


            }else{
                throw new UserWorksInCompanyException("Users works in another company", 404);
            }

        }else {
            throw new EntityNotFoundException("You are not admin!");
        }
    }

    @Override
    public void acceptInvitation(AcceptInvDTO acceptInvDTO) {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());

        Invitation invitation = invitationsRepo.findById(acceptInvDTO.getId()).orElseThrow(EntityNotFoundException::new);

        Company company = companyRepo.findById(invitation.getCompany().getId()).orElseThrow(EntityNotFoundException::new);

        User_role_in_company newUserInCompany = new User_role_in_company();

        newUserInCompany.setUser(user);
        Role_in_company role_in_company = new Role_in_company();
        role_in_company.setId(2);
        role_in_company.setName("participant");
        newUserInCompany.setRole_in_company(role_in_company);
        newUserInCompany.setDepartment(company);

        userRoleInCompanyRepo.save(newUserInCompany);

        invitationsRepo.delete(invitation);

    }

    @Override
    public UserDTO findUser(String userEmail) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(EntityNotFoundException::new);

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setBirth_day(user.getBirth_day());

        return userDTO;
    }

    @Override
    public Integer reject(Integer delId) {


        Invitation invitation = invitationsRepo.findById(delId).orElseThrow(EntityNotFoundException::new);

        invitationsRepo.delete(invitation);

        return invitation.getId();
    }
}
