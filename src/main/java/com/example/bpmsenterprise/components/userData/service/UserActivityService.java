package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import com.example.bpmsenterprise.components.userData.interfaces.IUserActivityControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActivityService implements IUserActivityControl {


    @Override
    public List<ViewUserAsWorker> getRelevantFor(String projectName, String startDate, String deadline, String specialization) {


        return null;
    }
}
