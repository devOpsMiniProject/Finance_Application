package org.example.financeproject;

import org.example.financeproject.Controller.UserController;
import org.example.financeproject.Model.User;
import org.example.financeproject.Service.BankingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BankingService bankingService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new User("testuser", "password123", null);
    }
    
    @Test
    void createUser_ValidUser_ReturnsCreated() throws Exception {
        // Given
        when(bankingService.addUser(any(User.class))).thenReturn(testUser);
        
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
        
        verify(bankingService, times(1)).addUser(any(User.class));
    }
    
    @Test
    void getUser_ExistingUsername_ReturnsUser() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        // When & Then
        mockMvc.perform(get("/api/users/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
        
        verify(bankingService, times(1)).getUser("testuser");
    }
    
    @Test
    void getUser_NonExistingUsername_ReturnsNotFound() throws Exception {
        // Given
        when(bankingService.getUser("nonexistent")).thenReturn(null);
        
        // When & Then
        mockMvc.perform(get("/api/users/nonexistent"))
                .andExpect(status().isNotFound());
        
        verify(bankingService, times(1)).getUser("nonexistent");
    }
    
    @Test
    void getAllUsers_ReturnsUserList() throws Exception {
        // Given
        List<User> users = Arrays.asList(testUser, new User("user2", "pass2", null));
        when(bankingService.getAllUsers()).thenReturn(users);
        
        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("testuser"))
                .andExpect(jsonPath("$[1].username").value("user2"));
        
        verify(bankingService, times(1)).getAllUsers();
    }
    
    @Test
    void deleteUser_ExistingUser_ReturnsNoContent() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        // When & Then
        mockMvc.perform(delete("/api/users/testuser"))
                .andExpect(status().isNoContent());
        
        verify(bankingService, times(1)).getUser("testuser");
    }
    
    @Test
    void deleteUser_NonExistingUser_ReturnsNotFound() throws Exception {
        // Given
        when(bankingService.getUser("nonexistent")).thenReturn(null);
        
        // When & Then
        mockMvc.perform(delete("/api/users/nonexistent"))
                .andExpect(status().isNotFound());
        
        verify(bankingService, times(1)).getUser("nonexistent");
    }
}