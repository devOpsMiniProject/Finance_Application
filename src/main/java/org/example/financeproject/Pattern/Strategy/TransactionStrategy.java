package org.example.financeproject.Pattern.Strategy;
import org.example.financeproject.Model.User;

public interface TransactionStrategy {
    void execute(User user, double amount);
}
