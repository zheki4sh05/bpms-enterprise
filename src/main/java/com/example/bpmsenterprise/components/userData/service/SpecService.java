package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.DTO.ChangeSpecDTO;
import com.example.bpmsenterprise.components.userData.DTO.SpecDTO;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Specialiazation;
import com.example.bpmsenterprise.components.userData.entity.UserSpecInCompany;
import com.example.bpmsenterprise.components.userData.interfaces.ISpecControl;
import com.example.bpmsenterprise.components.userData.repository.CompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.SpecCompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.UserSpecInCompanyRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecService implements ISpecControl {

    private final CompanyRepo companyRepo;
    private final SpecCompanyRepo specCompanyRepo;
    private final UserRepository userRepository;
    private final UserSpecInCompanyRepo userSpecInCompanyRepo;

    @Override
    public SpecDTO create(Integer companyId, String name) {

        Company company = companyRepo.findById(companyId).orElseThrow(EntityNotFoundException::new);

        Specialiazation specialiazation = new Specialiazation();

        specialiazation.setName(name);
        specialiazation.setCompany(company);

        Specialiazation saved = specCompanyRepo.save(specialiazation);

        SpecDTO specDTO = SpecDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .count(0)
                .build();

        return specDTO;
    }

    @Override
    public SpecDTO update(Integer id, String name, Integer count) {

        Specialiazation specialiazation = specCompanyRepo.findById(id).orElseThrow(EntityNotFoundException::new);
        specialiazation.setName(name);
        specCompanyRepo.save(specialiazation);
        SpecDTO specDTO = SpecDTO.builder()
                .id(specialiazation.getId())
                .name(specialiazation.getName())
                .count(count)
                .build();

        return specDTO;
    }

    @Override
    public Integer delete(Integer id) {

        specCompanyRepo.deleteById(id);

        return id;
    }

    @Override
    public List<SpecDTO> fetch(String name) {

        Company company = companyRepo.findBy(name).orElseThrow(EntityNotFoundException::new);

        List<SpecDTO> m = specCompanyRepo.getMap(company.getId());

        return m;
    }


    public List<SpecDTO> fetch(Integer id) {

        Company company = companyRepo.findById(id).orElseThrow(EntityNotFoundException::new);

        List<SpecDTO> m = specCompanyRepo.getMap(company.getId());

        return m;
    }

    @Override
    public List<SpecDTO> change(ChangeSpecDTO changeSpecDTO) {

        User user = userRepository.findByEmail(changeSpecDTO.getEmail()).orElseThrow(EntityNotFoundException::new);

        UserSpecInCompany userSpecInCompany = userSpecInCompanyRepo.findBySpecUserId(user.getId());
        Specialiazation specialiazation = specCompanyRepo.findById(changeSpecDTO.getSpec()).orElseThrow(EntityNotFoundException::new);
        if (userSpecInCompany != null) {
            userSpecInCompany.setSpecialiazation(specialiazation);
            userSpecInCompanyRepo.save(userSpecInCompany);
        } else {

            UserSpecInCompany saved = UserSpecInCompany.builder()

                    .user(user)
                    .specialiazation(specialiazation)
                    .build();

            userSpecInCompanyRepo.save(saved);

        }


        return fetch(changeSpecDTO.getCompany());
    }

}
