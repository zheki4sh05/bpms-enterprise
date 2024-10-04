package com.example.bpmsenterprise.components.documents.facade;

import com.example.bpmsenterprise.components.documents.DTO.*;

import java.util.*;

public interface IDocumentsFacade {
    List<DocumentInfoDTO> fetchDocumentsFromAllRecourses(String companyName, String type);
}
