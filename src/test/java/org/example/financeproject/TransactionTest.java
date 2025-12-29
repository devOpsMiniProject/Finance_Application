package org.example.financeproject;

import org.example.financeproject.Model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void testGettersAndSetters() {
        Transaction transaction = new Transaction();
        transaction.setDescription("Deposit");
        transaction.setAmount(250.0);
        transaction.setDate(LocalDateTime.now());

        assertEquals("Deposit", transaction.getDescription());
        assertEquals(250.0, transaction.getAmount());
        assertNotNull(transaction.getDate());

        Transaction transaction2 = new Transaction("Withdrawal", 100.0);
        assertEquals("Withdrawal", transaction2.getDescription());
        assertEquals(100.0, transaction2.getAmount());
        assertNotNull(transaction2.getDate());
    }
}
