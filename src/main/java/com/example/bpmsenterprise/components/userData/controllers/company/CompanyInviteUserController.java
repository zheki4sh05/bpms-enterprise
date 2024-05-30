package com.example.bpmsenterprise.components.userData.controllers.company;

import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.AcceptInvitationEntity;
import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.UserInviteResponseEntity;
import com.example.bpmsenterprise.components.userData.exceptions.UserWorksInCompanyException;
import com.example.bpmsenterprise.components.userData.interfaces.ICompanyHRControl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EntityNotFoundException e) { //
            return ResponseEntity.badRequest().header("error", "403").body(e.getMessage());
        } catch (UserWorksInCompanyException e) {
            return ResponseEntity.badRequest().header("error", String.valueOf(e.getCode())).body(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/accept_invitation")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> accept(@RequestHeader Map<String, String> headers,
                                    @RequestParam(value = "id") String userId) {

        try {
            companyHRControl.acceptInvitation(Integer.parseInt(userId));
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (EntityNotFoundException e) { //
            return ResponseEntity.badRequest().header("error", "404").body(e.getMessage());
        }
    }

    @GetMapping("/findUser")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getUserData(@RequestHeader Map<String, String> headers,
                                         @RequestParam(value = "email") String userEmail) {
        try {
            UserDTO userDTO = companyHRControl.findUser(userEmail);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND.value()), e),
                    HttpStatus.NOT_FOUND);
        }


    }

}