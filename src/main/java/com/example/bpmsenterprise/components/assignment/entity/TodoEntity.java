package com.example.bpmsenterprise.components.assignment.entity;

import com.example.bpmsenterprise.components.authentication.entity.User;
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
@Table(name = "todo")
public class TodoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "isDone")
    private Boolean isDone;


    @ManyToOne
    @JoinColumn(name = "assignmentId")
    private Assignment assignment;


}
