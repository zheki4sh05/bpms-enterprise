package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.AssignmentDocument;
import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.userData.entity.AssignmentNotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentHasDocRepo extends JpaRepository<AssignmentDocument, Integer> {
    @Query("""

                    select a.document_id.id
                    from AssignmentDocument a
                    where a.document_id.id= :docId

            """)
    List<Integer> getAssignmentsIdByDocId(@Param("docId") Integer id);

    @Query("""

                select doc.document_id 
                from AssignmentDocument doc
                where doc.assignment_id.id = :assignmentId

            """)
    List<DocumentEntity> getDocsByAssignmentId(@Param("assignmentId") Integer id);

    @Query("""
                select d
                from AssignmentDocument d
                where d.document_id.id = :docId
            """)
    AssignmentDocument findByDocId(@Param("docId") Integer docId);
}
