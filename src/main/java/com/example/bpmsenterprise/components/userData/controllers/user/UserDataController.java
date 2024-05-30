package com.example.bpmsenterprise.components.userData.controllers.user;

import com.example.bpmsenterprise.components.userData.DTO.NotificationDTO;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.exceptions.SuchEmailIsExistException;
import com.example.bpmsenterprise.components.userData.interfaces.IUserDataControl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserDataController {

    private final IUserDataControl userDataControl;

    @CrossOrigin
    @GetMapping("/")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getUserData(@RequestHeader Map<String, String> headers) {
        try {
            UserDTO user = userDataControl.fetch();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND.value()), e),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping("/update")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> updateUserData(@RequestHeader Map<String, String> headers,
                                            @RequestBody UserDTO userDTO) {

        try {
            userDataControl.update(userDTO);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND.value()), e),
                    HttpStatus.NOT_FOUND);
        } catch (SuchEmailIsExistException e) {
            return new ResponseEntity<>(new SuchEmailIsExistException(e.getMessage(), e.getCode()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/notification")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getUserNotification(@RequestHeader Map<String, String> headers,
                                                 @RequestParam(value = "email") String email) {

        try {
            List<NotificationDTO> list = userDataControl.getNotifByEmail(email);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new EntityNotFoundException(String.valueOf(HttpStatus.NOT_FOUND.value()), e),
                    HttpStatus.NOT_FOUND);
        }


    }


}
