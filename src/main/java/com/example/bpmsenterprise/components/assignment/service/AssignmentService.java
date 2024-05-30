package com.example.bpmsenterprise.components.assignment.service;

import com.example.bpmsenterprise.components.assignment.controller.CreateAssignmentRequest;
import com.example.bpmsenterprise.components.assignment.interfaces.IAssigmentControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService implements IAssigmentControl {
    @Override
    public Integer createNewAssignment(CreateAssignmentRequest assignmentRequest) {

        return null;
    }
}
