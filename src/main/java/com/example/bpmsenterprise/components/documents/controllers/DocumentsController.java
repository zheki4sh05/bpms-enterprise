package com.example.bpmsenterprise.components.documents.controllers;

import com.example.bpmsenterprise.components.assignment.controller.CreateAssignmentRequest;
import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.exceptions.DocumentUploadException;
import com.example.bpmsenterprise.components.documents.interfaces.IDocumentsControl;
import com.example.bpmsenterprise.components.documents.props.CreateDocRequest;
import com.example.bpmsenterprise.components.userData.entity.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/documents")
@RequiredArgsConstructor
public class DocumentsController {
    private final IDocumentsControl documentsControl;

    @CrossOrigin
    @PostMapping("/upload")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> uploadDocument(@RequestHeader Map<String, String> headers,
                                            @ModelAttribute CreateDocRequest createDocRequest) {

        try {
            List<DocumentInfoDTO> documentInfoDTOs = documentsControl.upload(createDocRequest);
            return ResponseEntity.ok(documentInfoDTOs);
        } catch (DataIntegrityViolationException | DocumentUploadException e) {
            return ResponseEntity.badRequest().header("error", "419").body(e.getMessage());
        }

    }

    @CrossOrigin
    @PostMapping("/fetch")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> fetchDocument(@RequestHeader Map<String, String> headers,
                                           @RequestParam(value = "alignment") String alignment,
                                           @RequestParam(value = "company") String company) {


        try {
            List<DocumentInfoDTO> documentInfoDTOs = documentsControl.fetch(alignment, company);
            return ResponseEntity.ok(documentInfoDTOs);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().header("error", "419").body(e.getMessage());
        }

    }


}
