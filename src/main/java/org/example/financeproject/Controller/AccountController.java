package org.example.financeproject.Controller;

import org.example.financeproject.Model.Account;
import org.example.financeproject.Model.User;
import org.example.financeproject.Service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private BankingService bankingService;

    @GetMapping("/{username}/balance")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable String username) {
        User user = bankingService.getUser(username);
        if (user == null) {
            return new ResponseEntity<>(
                    Map.of("error", "Utilisateur non trouvé"),
                    HttpStatus.NOT_FOUND
            );
        }

        Account account = user.getAccount();
        if (account == null) {
            return new ResponseEntity<>(
                    Map.of("error", "Compte non trouvé pour l'utilisateur"),
                    HttpStatus.NOT_FOUND
            );
        }

        // Utiliser HashMap pour éviter NPE avec null values
        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("balance", account.getBalance());

        // Ajouter accountId seulement si non null
        if (account.getId() != null) {
            response.put("accountId", account.getId());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{username}/balance")
    public ResponseEntity<Map<String, Object>> updateBalance(
            @PathVariable String username,
            @RequestBody Map<String, Object> requestBody) {

        User user = bankingService.getUser(username);
        if (user == null) {
            return new ResponseEntity<>(
                    Map.of("error", "Utilisateur non trouvé"),
                    HttpStatus.NOT_FOUND
            );
        }

        // Check if balance field exists
        if (!requestBody.containsKey("balance") || requestBody.get("balance") == null) {
            return new ResponseEntity<>(
                    Map.of("error", "Le champ 'balance' est requis"),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            double newBalance = Double.parseDouble(requestBody.get("balance").toString());
            user.getAccount().setBalance(newBalance);

            // Save the updated user
            bankingService.updateUser(user);  // You need to implement this method

            return new ResponseEntity<>(
                    Map.of(
                            "message", "Solde mis à jour avec succès",
                            "username", username,
                            "newBalance", newBalance
                    ),
                    HttpStatus.OK
            );

        } catch (NumberFormatException e) {
            return new ResponseEntity<>(
                    Map.of("error", "Format de solde invalide. Le solde doit être un nombre."),
                    HttpStatus.BAD_REQUEST
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("error", "Erreur lors de la mise à jour du solde: " + e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}