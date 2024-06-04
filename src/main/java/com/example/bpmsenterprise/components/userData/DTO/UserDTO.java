package com.example.bpmsenterprise.components.userData.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String firstname;
    private String lastname;
    private String surname;
    private String birth_day;
    private String email;
    private String phone;
}
