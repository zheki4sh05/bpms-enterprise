package com.example.bpmsenterprise.components.userData.DTO;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserCompany {
    private Integer id;
    private String name;
    private String desc;
    private String role;
    private List<SpecDTO> list;
}
