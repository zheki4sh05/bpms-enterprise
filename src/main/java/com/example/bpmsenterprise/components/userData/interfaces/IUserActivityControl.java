package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;

import java.util.List;

public interface IUserActivityControl {
    List<ViewUserAsWorker> getRelevantFor(Integer projectName, String startDate, String deadline, Integer specialization);
}
