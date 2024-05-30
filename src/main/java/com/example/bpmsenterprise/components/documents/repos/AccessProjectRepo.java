package com.example.bpmsenterprise.components.documents.repos;

import com.example.bpmsenterprise.components.documents.entity.access.AccessByCompany;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessProjectRepo extends JpaRepository<AccessByProject, Integer> {

    @Query(value = """
                
                select a 
                from AccessByProject a 
                where a.company.id= :compId and a.project.id= :prId

            """)
    List<AccessByProject> findByProjectIdAndCompanyId(@Param("compId") Integer companyId,
                                                      @Param("prId") Integer projectId);
}
