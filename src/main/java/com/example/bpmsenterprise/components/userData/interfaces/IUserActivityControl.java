package com.example.bpmsenterprise.components.userData.interfaces;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;

import java.util.List;

public interface IUserActivityControl {
    List<ViewUserAsWorker> getRelevantFor(String projectName, String startDate, String deadline, String specialization);
}
