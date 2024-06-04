package com.example.bpmsenterprise.components.documents.repos;

import com.example.bpmsenterprise.components.documents.entity.Type;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessPublicRepo extends JpaRepository<AccessByCompany, Integer> {

    @Query(value = """
                select a 
                from AccessByCompany a 
                where a.company.name = :companyName and a.documentEntity.type = :type
            """)
    List<AccessByCompany> findByCompanyName(@Param("companyName") String companyName, @Param("type") Type type);
}
