package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.assignment.repository.AssignmentNotifRepo;
import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;

import com.example.bpmsenterprise.components.userData.DTO.NotificationDTO;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import com.example.bpmsenterprise.components.userData.exceptions.SuchEmailIsExistException;
import com.example.bpmsenterprise.components.userData.interfaces.IUserDataControl;
import com.example.bpmsenterprise.components.userData.repository.InvitationsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserDataService implements IUserDataControl {
    private final UserData userData;
    private final UserRepository userRepository;
    private final InvitationsRepo invitationsRepo;
    private final AssignmentNotifRepo assignmentNotifRepo;

    @Override
    public UserDTO fetch() {
        User user = userData.getUserByEmail(userData.getCurrentUserEmail());
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
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

    @Override
    public List<NotificationDTO> getNotifByEmail(String email) {
        List<NotificationDTO> invitations = invitationsRepo.findByUserEmail(email);
        List<NotificationDTO> assignments = assignmentNotifRepo.findAllBySendToEmail(email);

        return Stream.concat(invitations.stream(), assignments.stream()).collect(Collectors.toList());
    }
}
