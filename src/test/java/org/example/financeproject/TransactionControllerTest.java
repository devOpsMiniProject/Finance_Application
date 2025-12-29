package org.example.financeproject;

import org.example.financeproject.Model.Account;
import org.example.financeproject.Controller.TransactionController;
import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Strategy.DepositStrategy;
import org.example.financeproject.Pattern.Strategy.TransactionStrategy;
import org.example.financeproject.Pattern.Strategy.TransferStrategy;
import org.example.financeproject.Pattern.Strategy.WithdrawStrategy;
import org.example.financeproject.Service.BankingService;
import org.example.financeproject.Service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private TransactionService transactionService;
    
    @MockBean
    private BankingService bankingService;
    
    @MockBean
    private DepositStrategy depositStrategy;
    
    @MockBean
    private WithdrawStrategy withdrawStrategy;
    
    @MockBean
    private TransferStrategy transferStrategy;
    
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
    void executeTransaction_DepositValid_ReturnsSuccess() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        doNothing().when(transactionService).execute(any(User.class), anyDouble(), any(TransactionStrategy.class));
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", 500.0);
        
        // When & Then
        mockMvc.perform(post("/api/transactions/testuser/DEPOSIT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transaction exécutée avec succès"))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.amount").value(500.0));
        
        verify(bankingService, times(1)).getUser("testuser");
        verify(transactionService, times(1)).execute(any(User.class), anyDouble(), any(TransactionStrategy.class));
    }
    
    @Test
    void executeTransaction_UserNotFound_ReturnsNotFound() throws Exception {
        // Given
        when(bankingService.getUser("nonexistent")).thenReturn(null);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", 500.0);
        
        // When & Then
        mockMvc.perform(post("/api/transactions/nonexistent/DEPOSIT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Utilisateur non trouvé"));
        
        verify(bankingService, times(1)).getUser("nonexistent");
        verify(transactionService, never()).execute(any(), anyDouble(), any());
    }
    
    @Test
    void executeTransaction_InvalidType_ReturnsBadRequest() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", 500.0);
        
        // When & Then
        mockMvc.perform(post("/api/transactions/testuser/INVALID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Type de transaction non supporté"));
        
        verify(bankingService, times(1)).getUser("testuser");
        verify(transactionService, never()).execute(any(), anyDouble(), any());
    }
    
    @Test
    void executeTransaction_StrategyThrowsException_ReturnsBadRequest() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        doThrow(new IllegalArgumentException("Montant invalide"))
            .when(transactionService).execute(any(User.class), anyDouble(), any(TransactionStrategy.class));
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("amount", -100.0); // Montant invalide
        
        // When & Then
        mockMvc.perform(post("/api/transactions/testuser/DEPOSIT")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Montant invalide"));
        
        verify(bankingService, times(1)).getUser("testuser");
        verify(transactionService, times(1)).execute(any(User.class), anyDouble(), any(TransactionStrategy.class));
    }
    
    @Test
    void getTransactionHistory_ExistingUser_ReturnsHistory() throws Exception {
        // Given
        when(bankingService.getUser("testuser")).thenReturn(testUser);
        
        // When & Then
        mockMvc.perform(get("/api/transactions/testuser/history"))
                .andExpect(status().isOk());
        
        verify(bankingService, times(1)).getUser("testuser");
    }
    
    @Test
    void getTransactionHistory_NonExistingUser_ReturnsNotFound() throws Exception {
        // Given
        when(bankingService.getUser("nonexistent")).thenReturn(null);
        
        // When & Then
        mockMvc.perform(get("/api/transactions/nonexistent/history"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Utilisateur non trouvé"));
        
        verify(bankingService, times(1)).getUser("nonexistent");
    }
}