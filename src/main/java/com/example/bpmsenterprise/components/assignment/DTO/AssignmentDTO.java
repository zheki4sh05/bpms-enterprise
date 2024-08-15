package com.example.bpmsenterprise.components.assignment.DTO;

import com.example.bpmsenterprise.components.assignment.entity.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO {

    private Integer id;

    private LocalDate startAt;

    private LocalDate createdAt;

    private String deadline;

    private String name;

    private String description;

    private Status status;

    private String statusName;

    private Integer user;

    private Integer worker;

    private Integer projectId;

    private String projectName;

    private Integer stageId;

    private String stageName;

    private String userEmail;


}
