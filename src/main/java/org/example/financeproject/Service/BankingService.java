package org.example.financeproject.Service;
import org.example.financeproject.Model.User;
import org.example.financeproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankingService {
    @Autowired
    private UserRepository userRepository;
    public User addUser(User user) { return userRepository.save(user); }
    public User getUser(String username) { return userRepository.findById(username).orElse(null); }
    public List<User> getAllUsers() { return userRepository.findAll(); }
    public User updateUser(User user) {return userRepository.save(user);}
}
