package com.example.bpmsenterprise.components.assignment.repository;

import com.example.bpmsenterprise.components.assignment.entity.Assignment;
import com.example.bpmsenterprise.components.assignment.entity.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssociationRepo extends JpaRepository<Association, Integer> {

    @Query("""

                        select a
                        from Association  a
                        where a.user.id = :userId

            """)
    List<Association> findAllByUserId(@Param("userId") Integer id);

    @Query("""

                        select a
                        from Association a
                        where a.worker.id = :userId

            """)
    List<Association> findAllByWorkerId(@Param("userId") Integer id);
}
