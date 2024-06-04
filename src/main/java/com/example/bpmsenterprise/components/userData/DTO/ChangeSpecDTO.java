package com.example.bpmsenterprise.components.userData.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeSpecDTO {

    private Integer company;
    private String email;

    private Integer spec;

}
