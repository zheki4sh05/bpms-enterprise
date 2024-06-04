package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.DTO.StagesDTO;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StagesRepo extends JpaRepository<Stage, Integer> {

    @Query("""

                select s
                from Stage s
                where s.project.id = :projectId
                order by s.order


            """)
    List<Stage> findByProjectId(@Param("projectId") Integer id);


}
