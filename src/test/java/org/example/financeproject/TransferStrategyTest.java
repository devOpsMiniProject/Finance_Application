package org.example.financeproject;

import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Strategy.TransferStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransferStrategyTest {

    @Test
    void testTransfer() {
        Account from = new Account();
        from.setBalance(500.0);
        User user1 = new User();
        user1.setAccount(from);

        Account to = new Account();
        to.setBalance(200.0);
        User user2 = new User();
        user2.setAccount(to);

        TransferStrategy transfer = new TransferStrategy();
        transfer.setToUser(user2);
        transfer.execute(user1, 150.0);

        assertEquals(350.0, from.getBalance());
        assertEquals(350.0, to.getBalance());
    }

    @Test
    void testTransferInsufficientFunds() {
        Account from = new Account();
        from.setBalance(100.0);
        User user1 = new User();
        user1.setAccount(from);

        Account to = new Account();
        to.setBalance(50.0);
        User user2 = new User();
        user2.setAccount(to);

        TransferStrategy transfer = new TransferStrategy();
        transfer.setToUser(user2);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transfer.execute(user1, 150.0);
        });

        assertEquals("Solde insuffisant", exception.getMessage());

        // Vérifie que les soldes n'ont pas changé
        assertEquals(100.0, from.getBalance());
        assertEquals(50.0, to.getBalance());
    }
}
