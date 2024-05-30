package com.example.bpmsenterprise.components.documents.entity.access;

import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import com.example.bpmsenterprise.components.documents.interfaces.AccessType;
import com.example.bpmsenterprise.components.userData.entity.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "access_by_company")
public class AccessByCompany implements AccessType {

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


    @Override
    public DocumentEntity getFile() {
        return documentEntity;
    }
}
