package org.example.financeproject.Pattern.Strategy;

import org.example.financeproject.Model.User;
import org.springframework.stereotype.Component;

@Component
public class DepositStrategy implements TransactionStrategy {
    @Override
    public void execute(User user, double amount) {
        user.getAccount().setBalance(user.getAccount().getBalance() + amount);
    }
}
