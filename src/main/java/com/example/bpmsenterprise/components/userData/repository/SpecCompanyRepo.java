package com.example.bpmsenterprise.components.userData.repository;

import com.example.bpmsenterprise.components.userData.DTO.SpecDTO;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.Specialiazation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.List;

public interface SpecCompanyRepo extends JpaRepository<Specialiazation, Integer> {

    @Query(value = """

                select new com.example.bpmsenterprise.components.userData.DTO.SpecDTO(s.id, s.name, (select count(usc.id) from UserSpecInCompany usc where usc.id = s.id)) 
                from Specialiazation s
                where s.company.id = :#{#companyId}
                group by s.id

            """)
    List<SpecDTO> getMap(@Param("companyId") Integer companyId);
}
