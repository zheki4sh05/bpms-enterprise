package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.DTO.NotificationDTO;
import com.example.bpmsenterprise.components.userData.entity.Invitation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface InvitationsRepo extends JpaRepository<Invitation, Integer> {
    @Query(value = """
                
                select new com.example.bpmsenterprise.components.userData.DTO.NotificationDTO(inv.id,"invite",inv.message, inv.date)
                from Invitation inv
                where inv.user.email = :email 

            """)
    ArrayList<NotificationDTO> findByUserEmail(@Param("email") String email);
}
