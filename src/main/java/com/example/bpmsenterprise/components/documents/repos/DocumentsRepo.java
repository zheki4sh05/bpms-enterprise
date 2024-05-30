package com.example.bpmsenterprise.components.documents.repos;

import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DocumentsRepo extends JpaRepository<DocumentEntity, Long> {


}
