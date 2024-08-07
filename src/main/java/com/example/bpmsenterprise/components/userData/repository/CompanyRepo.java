package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
    @Query("""
                select c from Company c 
                where c.name = :companyName
            """)
    Optional<Company> findBy(@Param("companyName") String name);
}
