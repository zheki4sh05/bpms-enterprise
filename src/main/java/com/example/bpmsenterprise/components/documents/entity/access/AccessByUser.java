package com.example.bpmsenterprise.components.documents.entity.access;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.documents.interfaces.AccessType;
import com.example.bpmsenterprise.components.userData.entity.Company;
import com.example.bpmsenterprise.components.userData.entity.Project;
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
@Table(name = "access_by_user")
public class AccessByUser implements AccessType {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "doc_id")
    private DocumentEntity documentEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "by_request")
    private Boolean byRequest;


    @Override
    public DocumentEntity getFile() {
        return documentEntity;
    }
}
