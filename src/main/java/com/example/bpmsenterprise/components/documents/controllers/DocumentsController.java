package com.example.bpmsenterprise.components.documents.controllers;


import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.DTO.DocumentSourceDTO;
import com.example.bpmsenterprise.components.documents.exceptions.DocumentUploadException;
import com.example.bpmsenterprise.components.documents.facade.*;
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
    private final IDocumentsFacade documentsFacade;

    @CrossOrigin
    @PostMapping("/upload")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> uploadDocument(@RequestHeader Map<String, String> headers,
                                            @ModelAttribute CreateDocRequest createDocRequest) {

        try {
            System.out.println(createDocRequest);
            List<DocumentInfoDTO> documentInfoDTOs = documentsControl.upload(createDocRequest);
            return ResponseEntity.ok(documentInfoDTOs);
        } catch (DataIntegrityViolationException | DocumentUploadException e) {
            return ResponseEntity.badRequest().header("error", "419").body(e.getMessage());
        }

    }

    @CrossOrigin
    @GetMapping("/fetch")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> fetchDocuments(@RequestHeader Map<String, String> headers,
                                            @RequestParam(value = "company") String company,
                                            @RequestParam(value = "type") String type) {

        try {
            // List<DocumentInfoDTO> documentInfoDTOs = documentsControl.fetch(company, type);

            List<DocumentInfoDTO> documentInfoDTOs = documentsFacade.fetchDocumentsFromAllRecourses(company, type);

            return ResponseEntity.ok(documentInfoDTOs);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().header("error", "419").body(e.getMessage());
        }

    }

    @CrossOrigin
    @GetMapping("/info")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> fetchDocument(@RequestHeader Map<String, String> headers,
                                           @RequestParam(value = "docId") Integer id,
                                           @RequestParam(value = "type") String type) {
        try {
            DocumentSourceDTO documentSourceDTO = documentsControl.getDocInfo(id, type);
            return ResponseEntity.ok(documentSourceDTO);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().header("error", "419").body(e.getMessage());
        }

    }


    @CrossOrigin
    @GetMapping("/doc_for_assignment")
    @PreAuthorize(value = "@cse.canAccessUser(#headers)")
    public ResponseEntity<?> moreInfo(@RequestHeader Map<String, String> headers,
                                      @RequestParam(value = "companyId") Integer companyId,
                                      @RequestParam(value = "userId") Integer userId,
                                      @RequestParam(value = "projectId") Integer projectId,
                                      @RequestParam(value = "type") String type) {
        try {
            List<DocumentInfoDTO> documentSourceDTO = documentsControl.docAssignment(companyId, userId, projectId, type);
            return ResponseEntity.ok(documentSourceDTO);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().header("error", "419").body(e.getMessage());
        }

    }


}
