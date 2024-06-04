package com.example.bpmsenterprise.components.userData.entity;

import com.example.bpmsenterprise.components.authentication.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "user_spec_in_company")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSpecInCompany {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "spec_id")
    private Specialiazation specialiazation;

}
