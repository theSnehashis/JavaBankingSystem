package com.resultflow.banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final String transactionId, accountNumber, description;
    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    public Transaction(String transactionId, String accountNumber, TransactionType type, double amount, String description) {
        this.transactionId = transactionId; this.accountNumber = accountNumber; this.type = type;
        this.amount = amount; this.description = description; this.timestamp = LocalDateTime.now();
    }
    @Override public String toString() {
        return String.format("%s | %s | %-13s | %10.2f | %s | %s", transactionId, accountNumber, type, amount,
                description, timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }
}