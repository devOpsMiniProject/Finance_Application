package org.example.financeproject;
import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Strategy.WithdrawStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WithdrawStrategyTest {

    @Test
    void testWithdrawSuccess() {
        Account account = new Account();
        account.setBalance(200.0);
        User u = new User();
        u.setAccount(account);
        WithdrawStrategy withdraw = new WithdrawStrategy();
        withdraw.execute(u, 100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void testWithdrawFail() {
        Account account = new Account();
        account.setBalance(50.0);
        User u = new User();
        u.setAccount(account);
        WithdrawStrategy withdraw = new WithdrawStrategy();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            withdraw.execute(u, 100.0);
        });
        assertEquals("Solde insuffisant", exception.getMessage());
        assertEquals(50.0, account.getBalance(), "Balance should not change if insufficient funds");
    }
}
