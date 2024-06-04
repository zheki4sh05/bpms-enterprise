package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.AssignmentDocument;
import com.example.bpmsenterprise.components.assignment.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepo extends JpaRepository<TodoEntity, Integer> {

    @Query("""

            select td
            from TodoEntity td
            where td.assignment.id = :assignment_id

            """)
    List<TodoEntity> findAllByAssignmentId(@Param("assignment_id") Integer id);
}
