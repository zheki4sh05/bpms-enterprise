package com.example.bpmsenterprise.components.authentication.repos;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = """
                select new com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker(u.id,r.role_in_company.name,u.firstname, u.lastname, u.email, 
                (select usc.specialiazation.id 
                from UserSpecInCompany  usc 
                where usc.user.id = u.id
                ))
                from  User u
                join User_role_in_company r on r.user.id = u.id and r.department.id= :companyId
            """)
    List<ViewUserAsWorker> findAllByDepartmentId(@Param("companyId") Integer id);

    @Query(value = """
                select new com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker(w.id,w.firstname, w.lastname, w.email)
                from User w
                join User_role_in_project urp on urp.project.id = :projectId and urp.user.id = w.id
            """)
    Optional<List<ViewUserAsWorker>> findAllByProjectId(@Param("projectId") Integer projectId);

    @Query("""

                    select new com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker(u.user.id)
                    from User_role_in_project  u
                    where u.project.id= :projectId and  u.role_in_project.id=1

            """)
    ViewUserAsWorker getLeaderById(@Param("projectId") Integer id);
}
