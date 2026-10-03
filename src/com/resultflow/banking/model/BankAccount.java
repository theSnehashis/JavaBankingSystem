package com.resultflow.banking.model;

import com.resultflow.banking.exception.InvalidAmountException;
import com.resultflow.banking.exception.InsufficientBalanceException;
import com.resultflow.banking.exception.InvalidAccountException;

import java.util.ArrayList;
import java.util.List;

public abstract class BankAccount {
    private final String accountNumber;
    private final String customerId;
    protected double balance;
    private AccountStatus status = AccountStatus.ACTIVE;
    private final List<Transaction> transactions = new ArrayList<>();

    protected BankAccount(String accountNumber, String customerId, double initialBalance) throws InvalidAmountException {
        if (initialBalance < 0) throw new InvalidAmountException("Initial balance cannot be negative.");
        this.accountNumber = accountNumber; this.customerId = customerId; this.balance = initialBalance;
    }
    public String getAccountNumber() { return accountNumber; }
    public String getCustomerId() { return customerId; }
    public double getBalance() { return balance; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public List<Transaction> getTransactions() { return List.copyOf(transactions); }

    public void deposit(double amount, String description) throws com.resultflow.banking.exception.BankingException {
        validateAmount(amount); validateActive();
        balance += amount;
        transactions.add(new Transaction("TX" + System.nanoTime(), accountNumber, TransactionType.DEPOSIT, amount, description));
    }
    public void withdraw(double amount, String description) throws com.resultflow.banking.exception.BankingException {
        validateAmount(amount); validateActive();
        if (amount > balance) throw new InsufficientBalanceException("Insufficient balance.");
        balance -= amount;
        transactions.add(new Transaction("TX" + System.nanoTime(), accountNumber, TransactionType.WITHDRAW, amount, description));
    }
    public void addTransaction(Transaction t) { transactions.add(t); }

    // Used only by BankingService after all transfer validations pass.
    public void reduceBalanceForTransfer(double amount) {
        balance -= amount;
    }

    // Used only by BankingService after all transfer validations pass.
    public void increaseBalanceForTransfer(double amount) {
        balance += amount;
    }
    protected void validateAmount(double amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Amount must be greater than zero.");
    }
    protected void validateActive() throws InvalidAccountException {
        if (status != AccountStatus.ACTIVE) throw new InvalidAccountException("Account is " + status + ".");
    }
    public abstract String getAccountType();
    public abstract double calculateBenefit();
    @Override public String toString() {
        return String.format("%s | %s | Customer: %s | Balance: %.2f | Status: %s", accountNumber, getAccountType(), customerId, balance, status);
    }
}