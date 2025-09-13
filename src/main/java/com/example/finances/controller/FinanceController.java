package com.example.finances.controller;

import com.example.finances.model.Transaction;
import com.example.finances.model.TransactionType;
import com.example.finances.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class FinanceController {
    private final TransactionRepository repository;
 // Dependency Injection - will allow to use methods without creating objects
    public FinanceController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String dashboard(Model model){
        List<Transaction> transactions = repository.findAll();

        double totalExpenses = transactions.stream().filter(transaction -> transaction.getType()== TransactionType.EXPENSE).mapToDouble(Transaction::getAmount).sum();
        double totalSavings = transactions.stream().filter(transaction -> transaction.getType()== TransactionType.SAVING).mapToDouble(Transaction::getAmount).sum();
        double totalInvestments = transactions.stream().filter(transaction -> transaction.getType()== TransactionType.INVESTMENT).mapToDouble(Transaction::getAmount).sum();

        // UI att
        model.addAttribute("transactions", transactions);
        model.addAttribute("totalExpenses", totalExpenses);
        model.addAttribute("totalSavings", totalSavings);
        model.addAttribute("totalInvestments", totalInvestments);
        model.addAttribute("newTransaction", new Transaction());
        return "index";
    }

    @PostMapping("/add")
    public String addTransaction(@ModelAttribute Transaction transaction){
        repository.save(transaction);
        return "redirect:/";
    }

}
