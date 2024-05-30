package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.userData.DTO.NotificationDTO;
import com.example.bpmsenterprise.components.userData.entity.AssignmentNotif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentNotifRepo extends JpaRepository<AssignmentNotif, Integer> {

    @Query("""
               select new com.example.bpmsenterprise.components.userData.DTO.NotificationDTO(an.id, "assignments",an.message,an.date, an.userFrom.email)
               from AssignmentNotif an
               where an.userTo.email = :email

            """)
    List<NotificationDTO> findAllBySendToEmail(@Param("email") String email);
}
