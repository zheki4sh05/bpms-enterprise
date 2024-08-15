package com.example.bpmsenterprise.components.assignment.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAssignmentStatusDTO {

    private Integer assignmentId;
    private String curStatus;
    private String newStatus;

}
