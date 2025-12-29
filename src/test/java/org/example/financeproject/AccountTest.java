package org.example.financeproject;

import org.example.financeproject.Model.Account;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testGettersAndSetters() {
        Account account = new Account();
        account.setBalance(1000.0);

        assertEquals(1000.0, account.getBalance());

        Account account2 = new Account(500.0);
        assertEquals(500.0, account2.getBalance());
    }
}
