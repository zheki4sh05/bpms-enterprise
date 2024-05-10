package com.example.bpmsenterprise.components.assignment.entity;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.userData.entity.Project;
import com.example.bpmsenterprise.components.userData.entity.Role_in_project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignment")
public class Association {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User user;

    @ManyToOne
    @JoinColumn(name = "worker")
    private User worker;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;


}
