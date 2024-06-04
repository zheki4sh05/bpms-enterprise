package com.example.bpmsenterprise.components.assignment.controller;

import com.example.bpmsenterprise.components.assignment.DTO.AssignmentDTO;
import com.example.bpmsenterprise.components.assignment.DTO.AssignmentDTOStatuses;
import com.example.bpmsenterprise.components.assignment.DTO.UpdateAssignmentDTO;
import com.example.bpmsenterprise.components.assignment.interfaces.IAssigmentControl;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<?> create(@RequestHeader Map<String, String> headers,
                                    @RequestBody CreateAssignmentRequest assignmentRequest) {

        try {
            Integer id = assigmentControl.createNewAssignment(assignmentRequest);
            return ResponseEntity.ok(id);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @GetMapping("/fetch")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> fetch(@RequestHeader Map<String, String> headers,
                                   @RequestParam("userEmail") String userEmail,
                                   @RequestParam("role") String role,
                                   @RequestParam("size") String size) {

        try {
            List<AssignmentDTO> list = assigmentControl.fetch(userEmail, role, size);
            return ResponseEntity.ok(list);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }


    @CrossOrigin
    @GetMapping("/assignment_statuses")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> assignmentStatuses(@RequestHeader Map<String, String> headers,
                                                @RequestParam("assignmentId") Integer id) {

        try {
            AssignmentDTOStatuses assignmentDTOStatuses = assigmentControl.assignmentStatuses(id);
            return ResponseEntity.ok(assignmentDTOStatuses);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @PostMapping("/update")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> update(@RequestHeader Map<String, String> headers,
                                    @RequestBody UpdateAssignmentDTO updateAssignment) {

        try {
            AssignmentDTO assignmentDTO = assigmentControl.update(updateAssignment);
            return ResponseEntity.ok(assignmentDTO);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }

    @CrossOrigin
    @DeleteMapping("/docDel")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> docDel(@RequestHeader Map<String, String> headers,
                                    @RequestParam("docId") Integer docId) {

        try {
            Integer id = assigmentControl.docDel(docId);
            return ResponseEntity.ok(id);
        } catch (DataIntegrityViolationException e) { // если у пользователя уже есть компания
            return ResponseEntity.badRequest().header("error", "419").body("already has company");
        }

    }


//    @CrossOrigin
//    @GetMapping("/fetch_statuses")
//    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
//    public ResponseEntity<?> fetchStatuses(@RequestHeader Map<String, String> headers,
//                                   @RequestParam("userEmail") String userEmail,
//                                   @RequestParam("role") String role,
//                                   @RequestParam("size") String size) {
//
//        try {
//            List<AssignmentDTO> list = assigmentControl.fetchStatuses(userEmail, role, size);
//            return ResponseEntity.ok(list);
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.badRequest().header("error", "419").body("already has company");
//        }
//
//    }


}
