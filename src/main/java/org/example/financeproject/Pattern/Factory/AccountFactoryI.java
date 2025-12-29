package org.example.financeproject.Pattern.Factory;

import org.example.financeproject.Model.Account;

public interface AccountFactoryI {
    Account createAccount(double initialBalance);
}
