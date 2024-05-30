package com.example.bpmsenterprise.components.documents.interfaces;

import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.exceptions.DocumentUploadException;
import com.example.bpmsenterprise.components.documents.props.CreateDocRequest;

import java.util.List;

public interface IDocumentsControl {
    List<DocumentInfoDTO> upload(CreateDocRequest createDocRequest) throws DocumentUploadException;

    List<DocumentInfoDTO> fetch(String alignment, String company);
}
