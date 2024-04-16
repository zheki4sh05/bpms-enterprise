package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.controllers.user.AcceptInvitationEntity;
import com.example.bpmsenterprise.components.userData.controllers.user.UserInviteResponseEntity;
import com.example.bpmsenterprise.components.userData.exceptions.UserWorksInCompanyException;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyHRControl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/company/")
@RequiredArgsConstructor
public class CompanyInviteUserController {

    private final ICompanyHRControl companyHRControl;

    @PostMapping("/invite")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> inviteUserByEmail(@RequestHeader Map<String, String> headers,
                                                    @RequestBody UserInviteResponseEntity userInviteResponseEntity) {

        try {
            companyHRControl.invite(userInviteResponseEntity);
            return ResponseEntity.ok(userInviteResponseEntity.getEmail());
        } catch (EntityNotFoundException e) { //
            return ResponseEntity.badRequest().header("error", "403").body(e.getMessage());
        } catch (UserWorksInCompanyException e) {
            return ResponseEntity.badRequest().header("error", String.valueOf(e.getCode())).body(e.getMessage());
        }
    }


    @PostMapping("/accept_invitation")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> accept(@RequestHeader Map<String, String> headers,
                                         @RequestBody AcceptInvitationEntity acceptInvitationEntity) {

        try {
            companyHRControl.acceptInvitation(acceptInvitationEntity);
            return ResponseEntity.ok(String.valueOf(acceptInvitationEntity.getId()));
        } catch (EntityNotFoundException e) { //
            return ResponseEntity.badRequest().header("error", "403").body(e.getMessage());
        }
    }

}