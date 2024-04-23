package com.example.bpmsenterprise.components.userData.service;

import com.example.bpmsenterprise.components.authentication.entity.User;
import com.example.bpmsenterprise.components.authentication.interfaces.UserData;
import com.example.bpmsenterprise.components.authentication.repos.UserRepository;
import com.example.bpmsenterprise.components.userData.DTO.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDataServiceTest {

    @Mock
    private UserData userData;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDataService userDataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetch() {

        User testUser = new User();
        testUser.setFirstname("John");
        testUser.setLastname("Doe");
        testUser.setSurname("Smith");
        testUser.setEmail("john.doe@example.com");
        testUser.setPhone("123-456-7890");
        testUser.setBirth_day("1990-01-01");

        when(userData.getCurrentUserEmail()).thenReturn("john.doe@example.com");
        when(userData.getUserByEmail("john.doe@example.com")).thenReturn(testUser);

        UserDTO userDTO = userDataService.fetch();

        assertEquals("John", userDTO.getFirstname());
        assertEquals("Doe", userDTO.getLastname());
        assertEquals("Smith", userDTO.getSurname());
        assertEquals("john.doe@example.com", userDTO.getEmail());
        assertEquals("123-456-7890", userDTO.getPhone());
        assertEquals("1990-01-01", userDTO.getBirth_day());
    }

    @Test
    void testUpdate() {
        User testUser = new User();
        testUser.setEmail("john.doe@example.com");

        when(userData.getCurrentUserEmail()).thenReturn("john.doe@example.com");
        when(userData.getUserByEmail("john.doe@example.com")).thenReturn(testUser);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("new.email@example.com");
        userDTO.setFirstname("Updated");
        userDTO.setLastname("User");
        userDTO.setSurname("Info");
        userDTO.setBirth_day("1990-01-01");
        userDTO.setPhone("987-654-3210");

        assertDoesNotThrow(() -> userDataService.update(userDTO));

        assertEquals("new.email@example.com", testUser.getEmail());
        assertEquals("Updated", testUser.getFirstname());
        assertEquals("User", testUser.getLastname());
        assertEquals("Info", testUser.getSurname());
        assertEquals("1990-01-01", testUser.getBirth_day());
        assertEquals("987-654-3210", testUser.getPhone());

        verify(userRepository, times(1)).save(testUser);
    }
}