package org.example.financeproject.Pattern.Observer;
import org.example.financeproject.Model.Transaction;

public interface TransactionObserver {
    void update(Transaction transaction);
}
