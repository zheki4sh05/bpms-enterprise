package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.Assignment;
import com.example.bpmsenterprise.components.assignment.entity.Association;
import com.example.bpmsenterprise.components.userData.entity.AssignmentNotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment, Integer> {


}
