package com.example.bpmsenterprise.components.userData.entity;

import com.example.bpmsenterprise.components.authentication.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter

@Table(name="user_role_in_company")
public class User_role_in_company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company department;

    @ManyToOne
    @JoinColumn(name = "id_role_in_company")
    private Role_in_company role_in_company;

}
