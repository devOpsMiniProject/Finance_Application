package org.example.financeproject;

import org.example.financeproject.Model.User;
import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testGettersAndSetters() {
        Account account = new Account(1000.0);
        List<Transaction> transactions = new ArrayList<>();

        User user = new User("john", "pass123", account);
        user.setTransactions(transactions);

        assertEquals("john", user.getUsername());
        assertEquals("pass123", user.getPassword());
        assertEquals(account, user.getAccount());
        assertEquals(transactions, user.getTransactions());

        // Modifier les attributs
        Account newAccount = new Account(500.0);
        user.setAccount(newAccount);
        user.setUsername("alice");
        user.setPassword("alice123");

        assertEquals("alice", user.getUsername());
        assertEquals("alice123", user.getPassword());
        assertEquals(newAccount, user.getAccount());
    }
}
