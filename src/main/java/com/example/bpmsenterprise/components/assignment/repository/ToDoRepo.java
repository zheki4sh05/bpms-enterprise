package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.AssignmentDocument;
import com.example.bpmsenterprise.components.assignment.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepo extends JpaRepository<TodoEntity, Integer> {


}
