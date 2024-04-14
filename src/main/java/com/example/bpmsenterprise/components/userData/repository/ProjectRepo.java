package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project,Integer> {

    @Query("""
        select p from Project p 
        where p.name = :projectName and p.company.name=:companyName
""")
   Project findByNameInCompany(@Param("projectName") String projectName,@Param("companyName") String companyName);
}
