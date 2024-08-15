package com.example.bpmsenterprise.components.assignment.DTO;

import com.example.bpmsenterprise.components.documents.DTO.ToDoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateTodosRequest {
    private Integer assignmentId;
    private List<ToDoDTO> todos;
}
