package com.example.bpmsenterprise.components.userData.controllers.project;

import com.example.bpmsenterprise.components.userData.DTO.CreateProject.CreateProjectDTO;
import com.example.bpmsenterprise.components.userData.DTO.ProjectStatusDTO;
import com.example.bpmsenterprise.components.userData.DTO.UserCompany;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.views.ViewProject;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.interfaces.IProjectControl;
import io.swagger.v3.oas.annotations.tags.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Project controller")
@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final IProjectControl projectControl;

    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> createProject(@RequestHeader Map<String, String> headers,
                                           @RequestBody CreateProjectDTO createProjectDTO) {
        try {
            Integer id = projectControl.createNewProject(createProjectDTO);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (EntityNotFoundException e) { //
            return ResponseEntity.badRequest().header("error", "404").body(e.getMessage());
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
        try {
            projectControl.updateProject(projectUpdateResponseEntity);
            return ResponseEntity.ok(projectUpdateResponseEntity.getName());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }
    }

    @GetMapping("/fetch")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getUserProjects(@RequestHeader Map<String, String> headers,
                                             @RequestParam(value = "companyName") String companyName) {

        try {
            List<ViewProject> projects = projectControl.getAllProjects(companyName);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }

    }

    @CrossOrigin
    @GetMapping("/workers")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getProjectWorkers(@RequestHeader Map<String, String> headers,
                                               @RequestParam(value = "projectId") Integer projectId) {

        try {
            List<ViewUserAsWorker> projects = projectControl.getAllWorkers(projectId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }

    }

    @CrossOrigin
    @GetMapping("/status")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> isProjectDone(@RequestHeader Map<String, String> headers,
                                           @RequestParam(value = "projectId") Integer projectId) {
        try {
            Double status = projectControl.projectsResult(projectId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }

    }

    @CrossOrigin
    @GetMapping("/overdue")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> isProjectOverdue(@RequestHeader Map<String, String> headers,
                                              @RequestParam(value = "projectId") Integer projectId) {
        try {
            Integer status = projectControl.projectsOverdue(projectId);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }

    }

    @CrossOrigin
    @GetMapping("/statuses")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> getProjectsStatuses(@RequestHeader Map<String, String> headers,
                                                 @RequestParam(value = "companyName") String companyName) {
        try {
            List<ProjectStatusDTO> list = projectControl.getProjectsStatuses(companyName);

            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().header("error", "404").body("doesn't have a project");
        }

    }
}
