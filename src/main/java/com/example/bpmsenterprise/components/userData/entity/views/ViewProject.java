package com.example.bpmsenterprise.components.userData.entity.views;

import com.example.bpmsenterprise.components.userData.DTO.StagesDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
public class ViewProject {
    private Integer id;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate deadline;
    private String role;
    private String color;
    private String roleName;
    private List<StagesDTO> stages;

    public ViewProject(Integer id, String name, String description, LocalDate createdAt, LocalDate deadline, String color) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.deadline = deadline;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public ViewProject() {
    }

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
