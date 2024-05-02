package com.example.bpmsenterprise.components.userData.entity.views;

import lombok.Data;

@Data
public class ViewUserAsWorker {
    private String firstname;
    private String lastname;
    private String email;

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

