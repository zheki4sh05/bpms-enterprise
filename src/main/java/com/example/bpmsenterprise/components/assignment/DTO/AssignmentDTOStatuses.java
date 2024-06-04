package com.example.bpmsenterprise.components.assignment.DTO;

import com.example.bpmsenterprise.components.documents.DTO.DocumentInfoDTO;
import com.example.bpmsenterprise.components.documents.DTO.ToDoDTO;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssignmentDTOStatuses {
    private ViewUserAsWorker viewUserAsWorker;
    private List<DocumentInfoDTO> documents;
    private List<DocumentInfoDTO> reports;
    private List<ToDoDTO> toDoDTOList;


}
