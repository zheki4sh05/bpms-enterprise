package com.example.bpmsenterprise.components.documents.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class DocumentSourceDTO {
    List<Integer> projects = new ArrayList<>();
    List<Integer> workers = new ArrayList<>();
    List<Integer> assignments = new ArrayList<>();


}
