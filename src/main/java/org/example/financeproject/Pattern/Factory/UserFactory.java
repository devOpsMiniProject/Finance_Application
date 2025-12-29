package org.example.financeproject.Pattern.Factory;
import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.User;

public class UserFactory implements UserFactoryI{

    @Override
    public User createUser(String username, String password, double initialBalance) {
        AccountFactoryI accountFactory = new AccountFactory();
        Account account = accountFactory.createAccount(initialBalance);
        return new User(username, password, account);
    }
}
