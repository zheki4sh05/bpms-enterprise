package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationsRepo extends JpaRepository<Invitation, Integer> {
}
