package org.example.financeproject.Pattern.Observer;
import org.example.financeproject.Model.Transaction;

public class NotificationService implements TransactionObserver {
    @Override
    public void update(Transaction transaction) {
        System.out.println("Notification: " + transaction);
    }
}
