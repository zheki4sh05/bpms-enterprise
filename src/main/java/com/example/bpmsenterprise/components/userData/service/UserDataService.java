package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;

import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.exceptions.SuchEmailIsExistException;
import com.example.bpmsenterprise.components.userData.interfaces.IUserDataControl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserDataService implements IUserDataControl {
    private final UserData userData;
    private final UserRepository userRepository;

    @Override
    public UserDTO fetch() {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstname(user.getFirstname());
        userDTO.setLastname(user.getLastname());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setBirth_day(user.getBirth_day());

        return userDTO;
    }

    @Override
    public void update(UserDTO userDTO) throws SuchEmailIsExistException {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        if (!Objects.equals(userDTO.getEmail(), user.getEmail())) {
            if (userData.getUserByEmail(userDTO.getEmail()) != null) {
                throw new SuchEmailIsExistException("NOT_UNIQUE", 419);
            } else {
                user.setEmail(userDTO.getEmail());
            }
        }
        user.setFirstname(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setSurname(userDTO.getSurname());
        user.setBirth_day(userDTO.getBirth_day());
        user.setPhone(userDTO.getPhone());

        userRepository.save(user);
    }
}
