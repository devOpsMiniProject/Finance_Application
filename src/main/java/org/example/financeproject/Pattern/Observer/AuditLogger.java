package org.example.financeproject.Pattern.Observer;
import org.example.financeproject.Model.Transaction;

public class AuditLogger implements TransactionObserver {
    @Override
    public void update(Transaction transaction) {
        System.out.println("Audit: " + transaction);
    }
}
