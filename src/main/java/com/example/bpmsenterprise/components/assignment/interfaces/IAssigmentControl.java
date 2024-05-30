package com.example.bpmsenterprise.components.assignment.interfaces;

import com.example.bpmsenterprise.components.assignment.controller.CreateAssignmentRequest;

public interface IAssigmentControl {
    Integer createNewAssignment(CreateAssignmentRequest assignmentRequest);
}
