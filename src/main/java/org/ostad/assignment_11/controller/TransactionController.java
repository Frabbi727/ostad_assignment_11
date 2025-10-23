package org.ostad.assignment_11.controller;

import org.ostad.assignment_11.model.Transaction;
import org.ostad.assignment_11.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // GET /api/transactions - Get all transactions
    // GET /api/transactions?type=income - Get transactions by type (optional)
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @RequestParam(required = false) String type) {

        if (type != null && !type.isEmpty()) {
            List<Transaction> filteredTransactions = transactionService.getTransactionsByType(type);
            return ok(filteredTransactions);
        }

        List<Transaction> transactions = transactionService.getAllTransactions();
        return ok(transactions);
    }

    // GET /api/transactions/{id} - Get a specific transaction
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    // POST /api/transactions - Add a new transaction
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.addTransaction(transaction);
        return status(HttpStatus.CREATED).body(createdTransaction);
    }

    // PUT /api/transactions/{id} - Update a specific transaction
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction transaction) {

        return transactionService.updateTransaction(id, transaction)
                .map(ResponseEntity::ok)
                .orElse(notFound().build());
    }

    // DELETE /api/transactions/{id} - Delete a specific transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        boolean deleted = transactionService.deleteTransaction(id);

        if (deleted) {
            return noContent().build();
        } else {
            return notFound().build();
        }
    }
}