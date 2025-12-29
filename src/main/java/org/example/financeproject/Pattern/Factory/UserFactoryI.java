package org.example.financeproject.Pattern.Factory;

import org.example.financeproject.Model.User;

public interface UserFactoryI {
    User createUser(String username, String password, double initialBalance);
}
