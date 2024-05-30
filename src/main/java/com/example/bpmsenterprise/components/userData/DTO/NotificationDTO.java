package com.example.bpmsenterprise.components.userData.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class NotificationDTO {
    private Integer id;
    private String type;
    private String message;
    private LocalDate date;
    private String sendFrom;

    public NotificationDTO(Integer id, String type, String message, LocalDate date) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.date = date;
    }

    public NotificationDTO(Integer id, String type, String message, LocalDate date, String sendFrom) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.date = date;
        this.sendFrom = sendFrom;
    }
}
