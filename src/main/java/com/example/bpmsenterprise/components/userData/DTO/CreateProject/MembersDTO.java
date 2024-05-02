package com.example.bpmsenterprise.components.userData.DTO.CreateProject;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MembersDTO {
    private List<ViewUserAsWorker> leader = new ArrayList<>();
    private List<ViewUserAsWorker> workers = new ArrayList<>();
}
