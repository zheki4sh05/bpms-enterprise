package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.DTO.ProjectStatusDTO;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.views.ViewProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Integer> {

 @Query("""
                 select p from Project p 
                 where p.name = :projectName and p.company.name=:companyName
         """)
 Project findByNameInCompany(@Param("projectName") String projectName, @Param("companyName") String companyName);

 @Query(value = """
                     
         select new com.example.bpmsenterprise.components.userData.entity.views.ViewProject(
             p.id,
             p.name,
             p.description,
             p.created_at,
             p.deadline,
             urp.role_in_project.name,
             p.color  
            ) 
         from Project  p
         join Company dep on dep.name = :companyName and dep.id = p.company.id
         join User_role_in_project urp on p.id = urp.project.id and urp.user.id= :id
                  
         """)
 Optional<List<ViewProject>> getByDepartmentNameAndUserId(@Param("id") Integer id, @Param("companyName") String companyName);

 @Query(value = """

              select count(s.id)/
                 (select count(s.id) 
                      from assignment s 
                      join association a on a.project_id = :#{#projectId} and a.assignment_id =s.id) * 100 
              from assignment s 
              join association a on a.project_id = :#{#projectId} and a.assignment_id =s.id and s.status_id = 1

         """, nativeQuery = true)
 Optional<Double> countResultById(@Param("projectId") Integer projectId);

 @Query(value = """

             select max(s.deadline)  >  (select deadline from project where id = :#{#projectId}) \s
             from assignment s
             join association a on a.project_id = :#{#projectId} and a.assignment_id =s.id;

         """, nativeQuery = true)
 Integer isOverdue(Integer projectId);

 @Query(value = """
             
              select new com.example.bpmsenterprise.components.userData.DTO.ProjectStatusDTO(p.id) 
                 from Project  p
                 join Company dep on dep.name = :companyName and dep.id = p.company.id
                 join User_role_in_project urp on p.id = urp.project.id and urp.user.id= :id
             
         """)
 Optional<List<ProjectStatusDTO>> findByDepartmentNameAndUserId(@Param("id") Integer id, @Param("companyName") String companyName);

    @Query(value = """
                select count(a.id)
                from Association a
                where a.project.id = :projectId

            """)
    Integer findAssignmentsByProjectId(@Param("projectId") Integer projectId);

    @Query("""

            select u.user.id
            from User_role_in_project  u
            where u.project= :projectId and  u.role_in_project.id=1

            """)
    Integer getLeaderById(@Param("projectId") Integer id);

    @Query(value = """

            select new com.example.bpmsenterprise.components.userData.entity.views.ViewProject(
                         p.id,
                         p.name,
                         p.description,
                         p.created_at,
                         p.deadline,
                         p.color  
                        ) 
                     from Project p
                     join Company dep on dep.name = :companyName and dep.id = p.company.id
                   
                   
            """)
    List<ViewProject> getAllByDepNameAndUserId(@Param("companyName") String companyName);

    @Query("""

             select new com.example.bpmsenterprise.components.userData.DTO.ProjectStatusDTO(p.id) 
                             from Project  p
                             join Company dep on dep.name = :companyName and dep.id = p.company.id
                        

            """)
    List<ProjectStatusDTO> findAllByDepartmentName(@Param("companyName") String companyName);
}
//       select new com.example.bpmsenterprise.components.userData.entity.views.ViewProject(
//               p.id,
//               p.name,
//               (select (count(s.id) / (select count(s.id) from Assignment s join Association a on a.project_id = p.id and a.assignment_id =s.id)) * 100 from Assignment s join Association a on a.project_id = p.id and a.assignment_id =s.id and s.status_id = 1),
//               ((select  count(s.id) from Assignment s join Association a on a.project.id = p.id and a.assignment.id = s.id) > 0),
//               p.deadline,
//               urp.
//               ((select max(s.deadline) from Assignment s join Association a on a.project.id = p.id and a.assignment.id = s.id) < p.deadline),
//
//        )
//        from Project p
//        join Company dep on dep.name =:companyName and dep.id = p.company.id
//        join User_role_in_project urp on p.id = urp.project.id and urp.user.id =:userId
//join department dep on dep.name = :#{#companyName} and dep.id = p.department_id
//        join user_role_in_project urp on p.id = urp.project_id and urp.user_id= :#{#id};