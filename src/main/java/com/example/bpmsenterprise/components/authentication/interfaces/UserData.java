package com.example.bpmsenterprise.components.authentication.interfaces;

import com.example.bpmsenterprise.components.authentication.entity.User;

import java.util.Optional;

public interface UserData {
    User getUserByEmail(String email);

    String getCurrentUserEmail();

    User getCurrentUser();
}
