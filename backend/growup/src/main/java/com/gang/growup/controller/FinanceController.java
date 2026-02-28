package com.gang.growup.controller;

import com.gang.growup.dto.TransactionDTO;
import com.gang.growup.entity.Transaction;
import com.gang.growup.entity.User;
import com.gang.growup.repository.TransactionRepository;
import com.gang.growup.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finance")
@CrossOrigin(origins = "http://localhost:5173")
public class FinanceController {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public FinanceController(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<Transaction> getTransactions(Authentication authentication) {
        String username = authentication.getName();
        return transactionRepository.findByUserUsername(username);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary(Authentication authentication) {
        String username = authentication.getName();
        Double income = transactionRepository.getTotalIncome(username);
        Double expenses = transactionRepository.getTotalExpenses(username);
        
        return ResponseEntity.ok(new Object() {
            public final Double totalIncome = income;
            public final Double totalExpenses = expenses;
            public final Double savings = income - expenses;
            public final Double savingsRate = income > 0 ? ((income - expenses) / income) * 100 : 0;
        });
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionDTO transactionDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction(
                transactionDTO.getType(),
                transactionDTO.getDescription(),
                transactionDTO.getAmount(),
                transactionDTO.getCategory(),
                transactionDTO.getDate(),
                user
        );

        return transactionRepository.save(transaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!transaction.getUser().getUsername().equals(username)) {
            return ResponseEntity.status(403).build();
        }

        transactionRepository.delete(transaction);
        return ResponseEntity.ok().build();
    }
}