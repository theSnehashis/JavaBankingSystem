package com.resultflow.banking.model;
import com.resultflow.banking.exception.InvalidAmountException;
public class CurrentAccount extends BankAccount {
    private final double overdraftLimit;
    public CurrentAccount(String no, String customerId, double initial, double limit) throws InvalidAmountException {
        super(no, customerId, initial); if (limit < 0) throw new InvalidAmountException("Overdraft limit cannot be negative."); this.overdraftLimit = limit;
    }
    public double getOverdraftLimit() { return overdraftLimit; }
    @Override public String getAccountType() { return "CURRENT"; }
    @Override public double calculateBenefit() { return overdraftLimit; }
}