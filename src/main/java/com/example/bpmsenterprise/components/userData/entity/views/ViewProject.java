package com.example.bpmsenterprise.components.userData.entity.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class ViewProject {

    private Integer id;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate deadline;
    private String role;
    private String color;

    public ViewProject(Integer id, String name, String description, LocalDate createdAt, LocalDate deadline, String role, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.role = role;
        this.color = color;
    }
}
