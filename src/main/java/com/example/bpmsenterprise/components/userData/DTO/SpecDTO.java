package com.example.bpmsenterprise.components.userData.DTO;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SpecDTO {
    private Integer id;
    private String name;
    private Integer count;

    public SpecDTO(Integer id, String name, Long count) {
        this.id = id;
        this.name = name;
        this.count = count.intValue();
    }

    public SpecDTO(Integer id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
