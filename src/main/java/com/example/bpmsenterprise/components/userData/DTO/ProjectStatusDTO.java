package com.example.bpmsenterprise.components.userData.DTO;

import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class ProjectStatusDTO {

    private Integer id;
    private Double done;
    private Integer isOverdue;
    private Integer isActive;
    private Integer leader;
    private List<Integer> workers;

    public ProjectStatusDTO(Integer id) {
        this.id = id;
    }
}
