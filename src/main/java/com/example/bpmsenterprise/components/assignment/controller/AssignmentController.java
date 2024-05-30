package com.example.bpmsenterprise.components.assignment.controller;

import com.example.bpmsenterprise.components.assignment.interfaces.IAssigmentControl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final IAssigmentControl assigmentControl;

    @CrossOrigin
    @PostMapping("/create")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> createCompany(@RequestHeader Map<String, String> headers,
                                           @RequestBody CreateAssignmentRequest assignmentRequest) {

        try {
            Integer id = assigmentControl.createNewAssignment(assignmentRequest);
            return ResponseEntity.ok(id);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

}
