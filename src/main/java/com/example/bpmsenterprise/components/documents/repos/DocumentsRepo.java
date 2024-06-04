package com.example.bpmsenterprise.components.documents.repos;

import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DocumentsRepo extends JpaRepository<DocumentEntity, Long> {


//    @Query("""
//    select d
//    from DocTypeEntity d
//
//
//""")
//    List<DocumentEntity> findByAdminAndParticipantId(Integer id, Integer userId);
}
