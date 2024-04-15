package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.entity.User_role_in_company;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface User_role_in_companyRepo extends JpaRepository<User_role_in_company,Integer> {

    @Query(value = """
      select u from User_role_in_company u 
      where u.user.id = :idUser and u.role_in_company.id = 1
      """)
    User_role_in_company findByUserIdAndWhereUserAdmin(Integer idUser) throws EntityNotFoundException;

    @Query(value = """
      select u from User_role_in_company u 
      where u.user.id = :idUser and u.role_in_company.id = 1 and u.department.id = :idComp   
      """)
    Optional<User_role_in_company> findByUserIdAndCompanyIdAndWhereUserAdmin(@Param("idUser") Integer idUser, @Param("idComp") Integer idComp) throws EntityNotFoundException;

    @Query(value= """
    select u from User_role_in_company u
    where u.user.email = :e
""")
    User_role_in_company findFreeUserByEmail(@Param("e") String email);
}
