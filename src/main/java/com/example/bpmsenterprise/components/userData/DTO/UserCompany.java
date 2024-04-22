package com.example.bpmsenterprise.components.userData.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCompany {
    private String name;
    private String desc;
    private String role;
}
