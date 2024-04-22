package com.example.bpmsenterprise.components.userData.controllers.user.requestEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectAddUserEntity {
    private String email;
    private String companyName;
    private String projectName;
}
