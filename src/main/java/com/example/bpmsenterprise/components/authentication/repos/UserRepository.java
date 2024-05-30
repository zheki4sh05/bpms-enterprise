package com.example.bpmsenterprise.components.authentication.repos;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = """
                select new com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker(u.id ,u.firstname,r.role_in_company.name, u.lastname, u.email)
                from  User u
                join User_role_in_company r on r.user.id = u.id and r.department.id= :companyId
            """)
    List<ViewUserAsWorker> findAllByDepartmentId(@Param("companyId") Integer id);

    @Query(value = """
                select new com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker(w.firstname, w.lastname, w.email)
                from User w
                join User_role_in_project urp on urp.project.id = :projectId and urp.user.id = w.id
            """)
    Optional<List<ViewUserAsWorker>> findAllByProjectId(@Param("projectId") Integer projectId);
}
