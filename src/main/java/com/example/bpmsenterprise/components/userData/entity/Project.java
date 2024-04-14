package com.example.bpmsenterprise.components.userData.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name="project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="created_at")
    private LocalDate created_at;

    @Column(name="deadline")
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Company company;


}
