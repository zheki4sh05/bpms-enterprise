package com.example.bpmsenterprise.components.documents.entity;

import com.example.bpmsenterprise.components.authentication.entity.Role;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.userData.entity.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "document")
public class DocumentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "load_at")
    private LocalDate loadAt;

    @Column(name = "path")
    private String path;

    @Column(name = "size")
    private Double size;

    @Column(name = "extension")
    private String extension;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Company company;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "uploaded_user")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;


}
