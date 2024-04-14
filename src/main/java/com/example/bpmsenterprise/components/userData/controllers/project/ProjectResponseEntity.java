package com.example.bpmsenterprise.components.userData.controllers.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponseEntity {
    private String name;
    private String description;
    private String startDate;
    private String finishDate;

}
