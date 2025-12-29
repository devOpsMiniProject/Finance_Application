package org.example.financeproject.Pattern.Strategy;

import org.example.financeproject.Model.User;
import org.springframework.stereotype.Component;

@Component
public class WithdrawStrategy implements TransactionStrategy {
    @Override
    public void execute(User user, double amount) {
        if (user.getAccount().getBalance() >= amount)
            user.getAccount().setBalance(user.getAccount().getBalance() - amount);
        else throw new RuntimeException("Solde insuffisant");
    }
}
