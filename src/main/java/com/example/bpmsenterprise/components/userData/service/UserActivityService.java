package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.entity.UserSpecInCompany;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.interfaces.IUserActivityControl;
import com.example.bpmsenterprise.components.userData.repository.ProjectRepo;
import com.example.bpmsenterprise.components.userData.repository.SpecCompanyRepo;
import com.example.bpmsenterprise.components.userData.repository.UserSpecInCompanyRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityService implements IUserActivityControl {

    private final UserRepository userRepository;
    private final UserSpecInCompanyRepo userSpecInCompanyRepo;
    private final SpecCompanyRepo specCompanyRepo;


    @Override
    public List<ViewUserAsWorker> getRelevantFor(Integer projectId, String startDate, String deadline, Integer specialization) {

        List<ViewUserAsWorker> relevant = new ArrayList<>();

        List<ViewUserAsWorker> allMembers = userRepository.findAllByProjectId(projectId).orElseThrow(EntityNotFoundException::new);

        allMembers.forEach(member -> {
            if (isRelevant(member, startDate, deadline, specialization)) {
                relevant.add(member);
            }
        });


        return relevant;
    }

    private boolean isRelevant(ViewUserAsWorker member, String startDate, String deadline, Integer specialization) {

        UserSpecInCompany userSpecInCompany = userSpecInCompanyRepo.findBySpecUserId(member.getId());
        return userSpecInCompany.getSpecialiazation().getName().equals(specCompanyRepo.findById(specialization).get().getName());
    }
}
