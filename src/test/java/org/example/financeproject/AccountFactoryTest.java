package org.example.financeproject;
import org.example.financeproject.Model.Account;
import org.example.financeproject.Pattern.Factory.AccountFactory;
import org.example.financeproject.Pattern.Factory.AccountFactoryI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountFactoryTest {

    @Test
    void testCreateAccount() {
        AccountFactoryI accountFactory = new AccountFactory();
        Account account = accountFactory.createAccount(500.0);
        assertNotNull(account);
        assertEquals(500.0, account.getBalance());
    }
}
