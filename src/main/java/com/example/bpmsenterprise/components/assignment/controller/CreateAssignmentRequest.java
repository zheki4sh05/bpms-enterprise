package com.example.bpmsenterprise.components.assignment.controller;

import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.DTO.ToDoDTO;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CreateAssignmentRequest {
    private Integer projectId;
    private Integer specialization;
    private String name;
    private String desc;
    private String startDate;
    private String finishDate;
    private Integer id;
    private List<Integer> reports = new ArrayList<>();
    private List<ToDoDTO> tasks = new ArrayList<>();
    private List<Integer> documents = new ArrayList<>();

}
