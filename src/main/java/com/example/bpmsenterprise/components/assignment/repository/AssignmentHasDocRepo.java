package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.AssignmentDocument;
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
}
