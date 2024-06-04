package com.example.bpmsenterprise.components.assignment.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateAssignmentDTO {
    private Integer id;
    private String name;
    private String desc;
    private String deadline;
    private LocalDate startAt;
    private String status;
    private Integer newStage;

}
