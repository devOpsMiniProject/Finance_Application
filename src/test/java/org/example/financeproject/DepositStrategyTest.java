package org.example.financeproject;

import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Strategy.DepositStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepositStrategyTest {

    @Test
    void testDeposit() {
        Account account = new Account();
        account.setBalance(100.0);
        User user = new User("test","test",account);
        DepositStrategy deposit = new DepositStrategy();
        deposit.execute(user, 50.0);
        assertEquals(150.0, account.getBalance());
    }
}
