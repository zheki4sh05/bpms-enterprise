package com.example.bpmsenterprise.components.userData.DTO.CreateProject;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProjectDTO {
    private AboutProjectDTO aboutProject;
    private DeadlineDTO deadline;
    private MembersDTO members;
}
