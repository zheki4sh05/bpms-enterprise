package com.example.bpmsenterprise.components.userData.controllers.project;

import com.example.bpmsenterprise.components.userData.interfaces.IProjectControl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
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
        }catch (EntityNotFoundException | NonUniqueResultException e){ //
            return  ResponseEntity.badRequest().header("error", "403").body(e.getMessage());
        }

    }

    @DeleteMapping("/delete")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> deleteProject(@RequestHeader Map<String, String> headers,
                                                @RequestBody ProjectResponseEntity projectResponseEntity) {

        try{
            projectControl.deleteProject(projectResponseEntity);
            return ResponseEntity.ok(projectResponseEntity.getName());
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().header("error", "403").body(e.getMessage());
        }

    }
    @PutMapping ("/update")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<String> updateProject(@RequestHeader Map<String, String> headers,
                                                    @RequestBody ProjectUpdateResponseEntity projectUpdateResponseEntity) {

        try{
            projectControl.updateProject(projectUpdateResponseEntity);
            return ResponseEntity.ok(projectUpdateResponseEntity.getName());
        }catch (EntityNotFoundException e){
            return  ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }

    }

}
