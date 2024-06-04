package com.example.bpmsenterprise.components.documents.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DocumentInfoDTO {
    private Integer id;
    private String name;
    private String format;
    private LocalDate downloadAt;
    private String access;
    private Double size;
    private String accessType;
    private Integer projectId;
    private List<Integer> users;

//    private String type;
//    private Integer uploadUserId;
}
