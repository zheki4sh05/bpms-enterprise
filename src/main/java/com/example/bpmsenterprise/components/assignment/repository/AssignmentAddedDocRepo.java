package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.AssignmentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentAddedDocRepo extends JpaRepository<AssignmentDocument, Integer> {


}
