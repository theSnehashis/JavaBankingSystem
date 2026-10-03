package com.resultflow.banking.model;

import com.resultflow.banking.exception.InvalidAccountException;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String customerId, name, username, password;
    private final List<String> accountNumbers = new ArrayList<>();
    private final List<String> beneficiaries = new ArrayList<>();

    public Customer(String customerId, String name, String username, String password) {
        this.customerId = customerId; this.name = name; this.username = username; this.password = password;
    }
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<String> getAccountNumbers() { return List.copyOf(accountNumbers); }
    public List<String> getBeneficiaries() { return List.copyOf(beneficiaries); }
    public void addAccountNumber(String n) { if (!accountNumbers.contains(n)) accountNumbers.add(n); }
    public boolean owns(String n) { return accountNumbers.contains(n); }
    public void addBeneficiary(String n) throws InvalidAccountException {
        if (beneficiaries.contains(n)) {
            throw new InvalidAccountException("Beneficiary already exists: " + n);
        }
        beneficiaries.add(n);
    }
    public void removeBeneficiary(String n) throws InvalidAccountException {
        if (!beneficiaries.remove(n)) {
            throw new InvalidAccountException("Beneficiary not found: " + n);
        }
    }
    public boolean hasBeneficiary(String n) { return beneficiaries.contains(n); }
    @Override public String toString() { return customerId + " | " + name + " | " + username + " | Accounts: " + accountNumbers; }
}