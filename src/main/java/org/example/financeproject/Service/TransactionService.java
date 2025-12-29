package org.example.financeproject.Service;
import org.example.financeproject.Model.Transaction;
import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Observer.TransactionObserver;
import org.example.financeproject.Pattern.Strategy.TransactionStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private List<TransactionObserver> observers = new ArrayList<>();

    public void addObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    public void execute(User user, double amount, TransactionStrategy strategy) {
        strategy.execute(user, amount);
        Transaction t = new Transaction(strategy.getClass().getSimpleName(), amount);
        user.getTransactions().add(t);
        observers.forEach(obs -> obs.update(t));
    }
}
