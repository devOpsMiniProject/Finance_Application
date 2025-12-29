package org.example.financeproject;
import org.example.financeproject.Model.Transaction;
import org.example.financeproject.Pattern.Observer.AuditLogger;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class AuditLoggerTest {

    @Test
    void testAuditLogger() {
        Transaction tx = new Transaction();
        tx.setAmount(100.0);
        tx.setDescription("DEPOSIT");
        tx.setDate(LocalDateTime.now());
        AuditLogger logger = new AuditLogger();
        logger.update(tx);
    }
}
