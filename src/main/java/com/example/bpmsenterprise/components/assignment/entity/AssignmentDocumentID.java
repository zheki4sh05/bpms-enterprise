package com.example.bpmsenterprise.components.assignment.entity;

import java.io.Serializable;

public class AssignmentDocumentID implements Serializable {
    private Integer document_id;
    private Integer assignment_id;

    public Integer getDocument_id() {
        return document_id;
    }

    public void setDocument_id(Integer document_id) {
        this.document_id = document_id;
    }

    public Integer getAssignment_id() {
        return assignment_id;
    }

    public void setAssignment_id(Integer assignment_id) {
        this.assignment_id = assignment_id;
    }

    public AssignmentDocumentID(Integer document_id, Integer assignment_id) {
        this.document_id = document_id;
        this.assignment_id = assignment_id;
    }

    public AssignmentDocumentID() {

    }
}
