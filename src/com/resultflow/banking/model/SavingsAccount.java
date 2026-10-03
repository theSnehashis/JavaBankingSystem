package com.resultflow.banking.model;
import com.resultflow.banking.exception.InvalidAmountException;
public class SavingsAccount extends BankAccount {
    private final double annualInterestRate;
    public SavingsAccount(String no, String customerId, double initial, double rate) throws InvalidAmountException {
        super(no, customerId, initial); this.annualInterestRate = rate;
    }
    public double calculateInterest() { return getBalance() * annualInterestRate / 100.0; }
    @Override public String getAccountType() { return "SAVINGS"; }
    @Override public double calculateBenefit() { return calculateInterest(); }
}