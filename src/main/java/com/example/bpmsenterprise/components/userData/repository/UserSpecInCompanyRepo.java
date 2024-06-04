package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.Specialiazation;
import com.example.bpmsenterprise.components.userData.entity.UserSpecInCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSpecInCompanyRepo extends JpaRepository<UserSpecInCompany, Integer> {

    @Query("""

                select usc
                from UserSpecInCompany usc 
                where usc.user.id = :userId

            """)
    UserSpecInCompany findBySpecUserId(@Param("userId") Integer id);
}
