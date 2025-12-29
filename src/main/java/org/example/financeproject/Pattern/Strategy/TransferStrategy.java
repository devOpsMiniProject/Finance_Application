package org.example.financeproject.Pattern.Strategy;
import org.example.financeproject.Model.User;
import org.springframework.stereotype.Component;

@Component
public class TransferStrategy implements TransactionStrategy {
    private User toUser;
    public TransferStrategy() {}

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    @Override
    public void execute(User fromUser, double amount) {
        if (fromUser.getAccount().getBalance() >= amount) {
            fromUser.getAccount().setBalance(fromUser.getAccount().getBalance() - amount);
            toUser.getAccount().setBalance(toUser.getAccount().getBalance() + amount);
        } else throw new RuntimeException("Solde insuffisant");
    }
}
