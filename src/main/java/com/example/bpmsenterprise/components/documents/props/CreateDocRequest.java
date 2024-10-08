package com.example.bpmsenterprise.components.documents.props;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CreateDocRequest {

    private String companyName;

    private Integer projectId;

    private String type;

    private List<MultipartFile> file;

    private String alignment;

    private Boolean byRequest;

    private List<ViewUserAsWorker> workers;

    private Integer assignmentId;

    private Integer uploadedUser;

    public String getType() {
        switch (this.type) {
            case "public" -> {
                return "Публичный";
            }
            case "project" -> {
                return "Проект";
            }
            case "user" -> {
                return "Личный";
            }
            default -> {
                return "Не задано";
            }
        }
    }

    public String getOriginalType() {
        return type;
    }
}
