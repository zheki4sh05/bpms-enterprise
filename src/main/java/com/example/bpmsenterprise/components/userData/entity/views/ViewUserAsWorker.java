package com.example.bpmsenterprise.components.userData.entity.views;

import lombok.Data;

import java.util.List;

@Data
public class ViewUserAsWorker {
    private Integer id;
    private String role;
    private String firstname;
    private String lastname;
    private String email;

    public ViewUserAsWorker() {
    }

    public ViewUserAsWorker(Integer id, String role, String firstname, String lastname, String email) {
        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public ViewUserAsWorker(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
}
//package com.example.bpmsenterprise.components.userData.entity.interfaces;
//
//public interface ViewUserAsWorker {
//     String getFirstname();
//     String getLastname();
//     String getEmail();
//}

