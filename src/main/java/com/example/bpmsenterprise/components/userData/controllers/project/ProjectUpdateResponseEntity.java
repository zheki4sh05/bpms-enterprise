package com.example.bpmsenterprise.components.userData.controllers.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor
public class ProjectUpdateResponseEntity extends ProjectResponseEntity{
    private String oldName;
}
