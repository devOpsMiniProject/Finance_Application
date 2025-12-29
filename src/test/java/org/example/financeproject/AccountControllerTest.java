package org.example.financeproject;

import org.example.financeproject.Controller.AccountController;
import org.example.financeproject.Model.Account;
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

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BankingService bankingService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private User testUser;
    private Account testAccount;
    
    @BeforeEach
    void setUp() {
        testAccount = new Account(1000.0);
        testUser = new User("testuser", "password123", testAccount);
    }
    
    @Test
    void getBalance_ExistingUser_ReturnsBalance() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        // When & Then
        mockMvc.perform(get("/api/accounts/testuser/balance"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.balance").value(1000.0));
        
        verify(bankingService, times(1)).getUser("testuser");
    }
    
    @Test
    void getBalance_NonExistingUser_ReturnsNotFound() throws Exception {
        // Given
        when(bankingService.getUser("nonexistent")).thenReturn(null);
        
        // When & Then
        mockMvc.perform(get("/api/accounts/nonexistent/balance"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Utilisateur non trouvé"));
        
        verify(bankingService, times(1)).getUser("nonexistent");
    }
    
    @Test
    void updateBalance_ValidRequest_ReturnsSuccess() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("balance", 1500.0);
        
        // When & Then
        mockMvc.perform(put("/api/accounts/testuser/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Solde mis à jour avec succès"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.newBalance").value(1500.0));
        
        verify(bankingService, times(1)).getUser("testuser");
    }
    
    @Test
    void updateBalance_NonExistingUser_ReturnsNotFound() throws Exception {
        // Given
        when(bankingService.getUser("nonexistent")).thenReturn(null);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("balance", 1500.0);
        
        // When & Then
        mockMvc.perform(put("/api/accounts/nonexistent/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Utilisateur non trouvé"));
        
        verify(bankingService, times(1)).getUser("nonexistent");
    }

    @Test
    void updateBalance_InvalidBalanceFormat_ReturnsBadRequest() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("balance", "invalid"); // Format invalide

        // When & Then
        mockMvc.perform(put("/api/accounts/testuser/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Format de solde invalide. Le solde doit être un nombre."));

        verify(bankingService, times(1)).getUser("testuser");
    }
    
    @Test
    void updateBalance_MissingBalanceField_ReturnsBadRequest() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("otherField", "value"); // Champ balance manquant
        
        // When & Then
        mockMvc.perform(put("/api/accounts/testuser/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest());
        
        verify(bankingService, times(1)).getUser("testuser");
    }


}