package com.example.bpmsenterprise.components.assignment.interfaces;

import com.example.bpmsenterprise.components.assignment.DTO.*;
import com.example.bpmsenterprise.components.assignment.controller.CreateAssignmentRequest;
import com.example.bpmsenterprise.components.assignment.entity.Assignment;
import com.example.bpmsenterprise.components.assignment.entity.AssignmentDocument;
import com.example.bpmsenterprise.components.documents.DTO.ToDoDTO;
import com.example.bpmsenterprise.components.documents.props.CreateDocRequest;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;

import java.util.List;

public interface IAssigmentControl {
    Integer createNewAssignment(CreateAssignmentRequest assignmentRequest);

    List<AssignmentDTO> fetch(String userEmail, String role, String size);

    AssignmentDTOStatuses assignmentStatuses(Integer id);

    AssignmentDTO update(UpdateAssignmentDTO updateAssignment);

    Integer docDel(Integer docId);

    List<ToDoDTO> updateTodos(UpdateTodosRequest updateTodosRequest);

    ViewUserAsWorker updateAssignmentWorker(UpdateAssignmentWorkerDTO updateTodosRequest);

    AssignmentDTO changeStatus(ChangeAssignmentStatusDTO changeAssignmentStatusDTO);


    List<AssignmentDocument> addDocsAssignment(List<Long> list, CreateDocRequest createDocRequest);
}
