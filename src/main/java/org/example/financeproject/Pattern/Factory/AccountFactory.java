package org.example.financeproject.Pattern.Factory;
import org.example.financeproject.Model.Account;

public class AccountFactory implements AccountFactoryI {
    @Override
    public Account createAccount(double initialBalance) {
        return new Account(initialBalance);
    }
}
