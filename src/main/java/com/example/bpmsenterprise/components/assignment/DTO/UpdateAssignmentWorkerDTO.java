package com.example.bpmsenterprise.components.assignment.DTO;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateAssignmentWorkerDTO {
    private Integer assignmentId;
    private Integer spec;
    private String role;
    private ViewUserAsWorker viewUserAsWorker;
}
