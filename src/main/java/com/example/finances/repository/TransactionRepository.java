package com.example.finances.repository;

import com.example.finances.model.Transaction;
import com.example.finances.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository <Transaction,Long> {
    List<Transaction> findByType(TransactionType type);
}
