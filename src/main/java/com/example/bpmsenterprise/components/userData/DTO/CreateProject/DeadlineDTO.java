package com.example.bpmsenterprise.components.userData.DTO.CreateProject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeadlineDTO {
    private String startDate;
    private String finishDate;
}
