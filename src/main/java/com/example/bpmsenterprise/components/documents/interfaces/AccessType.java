package com.example.bpmsenterprise.components.documents.interfaces;

import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AccessType {
    DocumentEntity getFile();
}
