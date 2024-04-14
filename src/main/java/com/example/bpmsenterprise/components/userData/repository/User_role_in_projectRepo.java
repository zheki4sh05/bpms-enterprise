package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.User_role_in_project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface User_role_in_projectRepo extends JpaRepository<User_role_in_project, Integer> {
    @Query("""
    select r from User_role_in_project r
    where r.role_in_project.id = 1 and r.user.id = :id and (select p.name from Project p where p.id = r.project.id) =:name
""")
   Optional<User_role_in_project> findByNameAndWhereUserAdmin(String name, @Param("id") Integer id);
}
