package com.example.bpmsenterprise.components.userData.controllers.company;


import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyRequest {
    private String name;
    private String desc;


}
