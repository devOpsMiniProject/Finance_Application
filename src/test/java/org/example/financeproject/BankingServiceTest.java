package org.example.financeproject;
import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.User;
import org.example.financeproject.Service.BankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.example.financeproject.Repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.*;

class BankingServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BankingService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User("john", "1234", new Account(500.0));
        when(userRepository.save(user)).thenReturn(user);

        User saved = service.addUser(user);

        assertNotNull(saved);
        assertEquals("john", saved.getUsername());
        assertEquals(500.0, saved.getAccount().getBalance());
    }

    @Test
    void testFindUser() {
        User user = new User("alice", "pwd", new Account(300.0));
        when(userRepository.findById("alice")).thenReturn(Optional.of(user));

        User found = service.getUser("alice");

        assertNotNull(found);
        assertEquals("alice", found.getUsername());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User("user1", "pass1", new Account(100.0));
        User user2 = new User("user2", "pass2", new Account(200.0));
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        assertEquals(2, service.getAllUsers().size());
    }
}
