package com.example.bpmsenterprise.components.userData.entity;

import com.example.bpmsenterprise.components.authentication.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "department")
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "send_from")
    private User sendFrom;

    @ManyToOne
    @JoinColumn(name = "send_to")
    private User sendTo;

    public Company(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}
