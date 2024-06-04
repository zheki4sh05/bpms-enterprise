package com.example.bpmsenterprise.components.documents.repos;

import com.example.bpmsenterprise.components.documents.entity.Type;
import com.example.bpmsenterprise.components.documents.entity.access.AccessByUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccessUserRepo extends JpaRepository<AccessByUser, Integer> {
    @Query(value = """
                
                select a
                from AccessByUser  a
                where a.user.id = :userId and a.company.id= :compId and a.documentEntity.type= :type
            """)
    List<AccessByUser> findByUserId(@Param("compId") Integer id,
                                    @Param("userId") Integer userId,
                                    @Param("type") Type type);

    @Query("""

                select a
                from AccessByUser a
                where a.documentEntity.id= :docId

            """)
    List<Integer> getUsersIdByDocId(@Param("docId") Integer id);


}
