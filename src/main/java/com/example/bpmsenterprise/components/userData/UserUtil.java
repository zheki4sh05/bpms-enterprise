package com.example.bpmsenterprise.components.userData;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.userData.entity.views.ViewUserAsWorker;

public class UserUtil {

    public static ViewUserAsWorker doMapFrom(User user) {

        ViewUserAsWorker viewUserAsWorker = ViewUserAsWorker.builder()
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .id(user.getId())

                .build();

        return viewUserAsWorker;

    }
}
