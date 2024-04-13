package com.example.bpmsenterprise.components.authentication.repos;

import com.example.bpmsenterprise.components.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);


}
