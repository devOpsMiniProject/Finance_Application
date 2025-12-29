package org.example.financeproject.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_app")
public class User {
    @Id
    private String username;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public User() {}
    public User(String username, String password, Account account) {
        this.username = username;
        this.password = password;
        this.account = account;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Account getAccount() { return account; }
    public List<Transaction> getTransactions() { return transactions; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
