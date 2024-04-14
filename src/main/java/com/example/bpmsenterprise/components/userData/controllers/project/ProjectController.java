package com.example.bpmsenterprise.components.userData.controllers.project;

import com.example.bpmsenterprise.components.userData.controllers.company.CreateCompanyRequest;
import com.example.bpmsenterprise.components.userData.interfaces.IProjectControl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final IProjectControl projectControl;
    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> createProject(@RequestHeader Map<String, String> headers,
                                                @RequestBody ProjectResponseEntity projectResponseEntity) {

        try{
            projectControl.createNewProject(projectResponseEntity);
            return ResponseEntity.ok(projectResponseEntity.getName());
        }catch (DataIntegrityViolationException e){ // если у пользователя уже есть компания
            return  ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

}
