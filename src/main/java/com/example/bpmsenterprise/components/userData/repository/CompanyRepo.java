package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company, Integer> {

}
