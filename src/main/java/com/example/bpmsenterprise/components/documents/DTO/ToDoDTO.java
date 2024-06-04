package com.example.bpmsenterprise.components.documents.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToDoDTO {

    private Long id;
    private String val;
    private Boolean isDone;

}
