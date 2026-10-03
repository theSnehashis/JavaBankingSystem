package com.resultflow.banking.service;

import com.resultflow.banking.exception.*;
import com.resultflow.banking.model.*;
import java.util.*;

public class BankingService {
    private final Map<String, Customer> customers = new LinkedHashMap<>();
    private final Map<String, BankAccount> accounts = new LinkedHashMap<>();

    public void addCustomer(Customer c) { customers.put(c.getCustomerId(), c); }
    public Customer findCustomer(String username) {
        return customers.values().stream().filter(c -> c.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }
    public Customer findCustomerById(String id) { return customers.get(id); }
    public BankAccount findAccount(String no) { return accounts.get(no); }
    public Collection<Customer> getCustomers() { return customers.values(); }
    public Collection<BankAccount> getAccounts() { return accounts.values(); }

    public void addAccount(BankAccount a) throws InvalidAccountException {
        if (accounts.containsKey(a.getAccountNumber())) throw new InvalidAccountException("Account number already exists.");
        accounts.put(a.getAccountNumber(), a);
    }

    public void deposit(String no, double amount, String desc) throws BankingException {
        BankAccount a = require(no); a.deposit(amount, desc);
    }

    public void withdraw(String no, double amount, String desc) throws BankingException {
        BankAccount a = require(no); a.withdraw(amount, desc);
    }

    public void transfer(String from, String to, double amount) throws BankingException {
        BankAccount sender = require(from), receiver = require(to);

        if (from.equals(to)) {
            throw new InvalidAccountException("Sender and receiver cannot be the same.");
        }

        if (sender.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Sender account is " + sender.getStatus() + ".");
        }
        if (receiver.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountException("Receiver account is " + receiver.getStatus() + ".");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero.");
        }
        if (amount > sender.getBalance()) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        sender.reduceBalanceForTransfer(amount);
        receiver.increaseBalanceForTransfer(amount);

        sender.addTransaction(new Transaction(
                "TX" + System.nanoTime(), from, TransactionType.TRANSFER_OUT,
                amount, "Transfer to " + to));

        receiver.addTransaction(new Transaction(
                "TX" + System.nanoTime(), to, TransactionType.TRANSFER_IN,
                amount, "Transfer from " + from));
    }

    public void validateBeneficiary(Customer c, String account) throws BankingException {
        BankAccount a = require(account);
        if (c.owns(account)) throw new InvalidAccountException("You cannot add your own account as beneficiary.");
        if (a.getStatus() != AccountStatus.ACTIVE) throw new InvalidAccountException("Beneficiary account is not active.");
    }

    public List<Transaction> getTransactions(String no) throws BankingException { return require(no).getTransactions(); }

    public void changeStatus(String no, AccountStatus status) throws BankingException {
        require(no).setStatus(status);
    }

    public BankAccount createAccountForCustomer(String customerId, String type, double initial) throws BankingException {
        Customer c = findCustomerById(customerId);
        if (c == null) throw new InvalidAccountException("Customer not found.");
        String no = type.substring(0, Math.min(2, type.length())) + System.nanoTime();
        BankAccount a;
        switch (type) {
            case "SAVINGS" -> a = new SavingsAccount(no, customerId, initial, 4.0);
            case "CURRENT" -> a = new CurrentAccount(no, customerId, initial, 2000);
            case "STUDENT" -> a = new StudentAccount(no, customerId, initial);
            case "FD" -> a = new FixedDepositAccount(no, customerId, initial, 7.0, 12);
            default -> throw new InvalidAccountException("Unknown account type.");
        }
        addAccount(a); c.addAccountNumber(no); return a;
    }

    private BankAccount require(String no) throws AccountNotFoundException {
        BankAccount a = accounts.get(no);
        if (a == null) throw new AccountNotFoundException("Account not found: " + no);
        return a;
    }
}