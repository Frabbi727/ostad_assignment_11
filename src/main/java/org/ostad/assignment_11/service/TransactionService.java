package org.ostad.assignment_11.service;

import org.ostad.assignment_11.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TransactionService {
    private final List<Transaction> transactions = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    // Get transactions by type (using Stream API)
    public List<Transaction> getTransactionsByType(String type) {
        return transactions.stream()
                .filter(transaction -> transaction.getType().equalsIgnoreCase(type))
                .toList();
    }

    // Get transaction by ID (using Stream API)
    public Optional<Transaction> getTransactionById(Long id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst();
    }

    // Add new transaction
    public Transaction addTransaction(Transaction transaction) {
        transaction.setId(idGenerator.getAndIncrement());
        transactions.add(transaction);
        return transaction;
    }

    // Update transaction (using Stream API)
    public Optional<Transaction> updateTransaction(Long id, Transaction updatedTransaction) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst()
                .map(existingTransaction -> {
                    existingTransaction.setDescription(updatedTransaction.getDescription());
                    existingTransaction.setAmount(updatedTransaction.getAmount());
                    existingTransaction.setType(updatedTransaction.getType());
                    existingTransaction.setDate(updatedTransaction.getDate());
                    return existingTransaction;
                });
    }

    // Delete transaction (using Stream API)
    public boolean deleteTransaction(Long id) {
        return transactions.removeIf(transaction -> transaction.getId().equals(id));
    }
}