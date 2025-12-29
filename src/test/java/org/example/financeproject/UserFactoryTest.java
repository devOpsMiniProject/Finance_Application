package org.example.financeproject;


import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Factory.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {

    @Test
    void testCreateUser() {
        User user = new UserFactory().createUser("testuser", "pass123", 1000.0);
        assertNotNull(user);
        assertEquals("testuser", user.getUsername());
        assertEquals("pass123", user.getPassword());
        assertNotNull(user.getAccount());
        assertEquals(1000.0, user.getAccount().getBalance());
        assertEquals(user, user);
    }
}
