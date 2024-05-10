package com.example.bpmsenterprise.components.userData.DTO.CreateProject;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {
    private String name;
    private String desc;
    private String color;
    private String startDate;
    private String finishDate;
    private List<ViewUserAsWorker> leader = new ArrayList<>();
    private List<ViewUserAsWorker> workers = new ArrayList<>();
}
