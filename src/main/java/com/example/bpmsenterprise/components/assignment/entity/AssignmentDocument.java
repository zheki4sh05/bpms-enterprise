package com.example.bpmsenterprise.components.assignment.entity;

import com.example.bpmsenterprise.components.documents.entity.DocumentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(AssignmentDocumentID.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignment_has_document")
public class AssignmentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name = "document_id")
    private DocumentEntity document_id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @ManyToOne
    @JoinColumn(name = "assignment_id")

    private Assignment assignment_id;

}
