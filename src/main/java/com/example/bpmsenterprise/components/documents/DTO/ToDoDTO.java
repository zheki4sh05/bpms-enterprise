package com.example.bpmsenterprise.components.documents.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToDoDTO {

    private Long id;
    private String val;
    private Boolean isDone;
    private Integer assignmentId;

}
