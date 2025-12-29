package org.example.financeproject;
import org.example.financeproject.Model.Transaction;
import org.example.financeproject.Pattern.Observer.NotificationService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class NotificationServiceTest {

    @Test
    void testNotification() {
        Transaction tx = new Transaction();
        tx.setAmount(50.0);
        tx.setDate(LocalDateTime.now());
        NotificationService notification = new NotificationService();
        notification.update(tx);
    }
}
