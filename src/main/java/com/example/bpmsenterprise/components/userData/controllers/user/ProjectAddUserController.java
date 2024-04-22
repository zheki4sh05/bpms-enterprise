package com.example.bpmsenterprise.components.userData.controllers.user;

import com.example.bpmsenterprise.components.userData.controllers.user.requestEntity.ProjectAddUserEntity;
import com.example.bpmsenterprise.components.userData.interfaces.IProjectHRControl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/project/")
@RequiredArgsConstructor
public class ProjectAddUserController {


    private final IProjectHRControl projectControl;

    @PostMapping("/include")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> includeUserByEmail(@RequestHeader Map<String, String> headers,
                                                     @RequestBody ProjectAddUserEntity projectAddUserEntity) {

        try {
            projectControl.include(projectAddUserEntity);
            return ResponseEntity.ok(projectAddUserEntity.getEmail());
        } catch (EntityNotFoundException e) { //
            return ResponseEntity.badRequest().header("error", "403").body(e.getMessage());
        }
    }


}
