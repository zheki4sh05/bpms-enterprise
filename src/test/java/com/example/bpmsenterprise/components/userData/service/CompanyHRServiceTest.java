package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.AcceptInvitationEntity;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.UserInviteResponseEntity;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Invitation;
import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import com.example.bpmsenterprise.components.userData.exceptions.UserWorksInCompanyException;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.InvitationsRepo;
import com.example.bpmsenterprise.components.userData.repository.User_role_in_companyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompanyHRServiceTest {
    @InjectMocks
    private CompanyHRService companyHRService;
    @Mock
    private UserData userData;
    @Mock
    private UserRepository userRepository;
    @Mock
    private InvitationsRepo invitationsRepo;
    @Mock
    private User_role_in_companyRepo userRoleInCompanyRepo;
    @Mock
    private CompanyRepo companyRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvite_ValidUser_AdminRole() throws UserWorksInCompanyException {
        User adminUser = new User();
        adminUser.setId(1);
        when(userData.getCurrentUserEmail()).thenReturn("onlyforward05@mail.ru");
        when(userData.getUserByEmail("onlyforward05@mail.ru")).thenReturn(adminUser);
        UserInviteResponseEntity inviteResponseEntity = new UserInviteResponseEntity();
        inviteResponseEntity.setEmail("newuser@example.com");
        inviteResponseEntity.setCompanyName("MyCompany");
        User invitedUser = new User();
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(java.util.Optional.of(invitedUser));
        when(userRoleInCompanyRepo.findByUserIdAndWhereUserAdmin(1)).thenReturn(new User_role_in_company());
        assertDoesNotThrow(() -> companyHRService.invite(inviteResponseEntity));
        verify(invitationsRepo, times(1)).save(any(Invitation.class));
    }

    @Test
    void testInvite_ValidUser_NonAdminRole() {
        User nonAdminUser = new User();
        nonAdminUser.setId(2);
        when(userData.getCurrentUserEmail()).thenReturn("user@example.com");
        when(userData.getUserByEmail("user@example.com")).thenReturn(nonAdminUser);
        UserInviteResponseEntity inviteResponseEntity = new UserInviteResponseEntity();
        inviteResponseEntity.setEmail("newuser@example.com");
        inviteResponseEntity.setCompanyName("MyCompany");
        assertThrows(EntityNotFoundException.class, () -> companyHRService.invite(inviteResponseEntity));
    }

    @Test
    void testAcceptInvitation_ValidInvitation() {
        Invitation invitation = new Invitation();
        invitation.setId(1);
        invitation.setCompany(new Company());
        when(invitationsRepo.findById(1)).thenReturn(java.util.Optional.of(invitation));
        AcceptInvitationEntity acceptInvitationEntity = new AcceptInvitationEntity();
        acceptInvitationEntity.setId(1);
        assertDoesNotThrow(() -> companyHRService.acceptInvitation(acceptInvitationEntity));
    }

    @Test
    void testAcceptInvitation_InvalidInvitation() {
        when(invitationsRepo.findById(2)).thenReturn(java.util.Optional.empty());
        AcceptInvitationEntity acceptInvitationEntity = new AcceptInvitationEntity();
        acceptInvitationEntity.setId(2);
        assertThrows(EntityNotFoundException.class, () -> companyHRService.acceptInvitation(acceptInvitationEntity));
    }
}