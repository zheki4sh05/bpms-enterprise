package com.example.bpmsenterprise.components.assignment.interfaces;

import com.example.bpmsenterprise.components.assignment.DTO.AssignmentDTO;
import com.example.bpmsenterprise.components.assignment.DTO.AssignmentDTOStatuses;
import com.example.bpmsenterprise.components.assignment.DTO.UpdateAssignmentDTO;
import com.example.bpmsenterprise.components.assignment.controller.CreateAssignmentRequest;

import java.util.List;

public interface IAssigmentControl {
    Integer createNewAssignment(CreateAssignmentRequest assignmentRequest);

    List<AssignmentDTO> fetch(String userEmail, String role, String size);

    AssignmentDTOStatuses assignmentStatuses(Integer id);

    AssignmentDTO update(UpdateAssignmentDTO updateAssignment);

    Integer docDel(Integer docId);
}
