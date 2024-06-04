package com.example.bpmsenterprise.components.userData.entity.views;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ViewUserAsWorker {
    private Integer id;
    private String role;
    private String firstname;
    private String lastname;
    private String email;
    private Integer spec;

    public ViewUserAsWorker(Integer id) {
        this.id = id;
    }

    public ViewUserAsWorker(Integer id, String role, String firstname, String lastname, String email, Integer spec) {
        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.spec = spec;
    }

    public ViewUserAsWorker() {
    }

    public ViewUserAsWorker(Integer id, String role, String firstname, String lastname, String email) {
        this.id = id;
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public ViewUserAsWorker(Integer id, String firstname, String lastname, String email) {
        this.id = id;
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

