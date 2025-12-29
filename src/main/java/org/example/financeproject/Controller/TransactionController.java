package org.example.financeproject.controller;

import org.example.financeproject.Model.Transaction;
import org.example.financeproject.Model.User;
import org.example.financeproject.Pattern.Strategy.DepositStrategy;
import org.example.financeproject.Pattern.Strategy.TransactionStrategy;
import org.example.financeproject.Pattern.Strategy.TransferStrategy;
import org.example.financeproject.Pattern.Strategy.WithdrawStrategy;
import org.example.financeproject.Service.BankingService;
import org.example.financeproject.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankingService bankingService;

    private final Map<String, TransactionStrategy> strategyMap = new HashMap<>();

    @Autowired
    public TransactionController(DepositStrategy depositStrategy, WithdrawStrategy withdrawStrategy, TransferStrategy transferStrategy) {
        strategyMap.put("DEPOSIT", depositStrategy);
        strategyMap.put("WITHDRAW", withdrawStrategy);
        strategyMap.put("TRANSFER", transferStrategy);
    }

    @PostMapping("/{username}/{type}")
    public ResponseEntity<Map<String, Object>> executeTransaction(
            @PathVariable String username,
            @PathVariable String type,
            @RequestBody Map<String, Object> requestBody) {

        try {
            User user = bankingService.getUser(username);
            if (user == null) {
                return new ResponseEntity<>(
                        Map.of("error", "Utilisateur non trouvé"),
                        HttpStatus.NOT_FOUND
                );
            }

            TransactionStrategy strategy = strategyMap.get(type.toUpperCase());
            if (strategy == null) {
                return new ResponseEntity<>(
                        Map.of("error", "Type de transaction non supporté"),
                        HttpStatus.BAD_REQUEST
                );
            }

            double amount = Double.parseDouble(requestBody.get("amount").toString());

            transactionService.execute(user, amount, strategy);

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Transaction exécutée avec succès",
                            "username", username,
                            "type", type,
                            "amount", amount,
                            "newBalance", user.getAccount().getBalance()
                    ),
                    HttpStatus.OK
            );

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    Map.of("error", e.getMessage()),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", "Erreur interne du serveur"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @GetMapping("/{username}/history")
    public ResponseEntity<?> getTransactionHistory(@PathVariable String username) {
        User user = bankingService.getUser(username);
        if (user == null) {
            return new ResponseEntity<>(
                    Map.of("error", "Utilisateur non trouvé"),
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(user.getTransactions(), HttpStatus.OK);
    }
}