package com.example.bpmsenterprise.components.userData.entity;

import com.example.bpmsenterprise.components.authentication.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "assignment_notif")
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentNotif {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message")
    private String message;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_from")
    private User userFrom;

    @ManyToOne
    @JoinColumn(name = "user_to")
    private User userTo;


}
