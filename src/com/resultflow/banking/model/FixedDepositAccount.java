package com.resultflow.banking.model;
import com.resultflow.banking.exception.InvalidAmountException;
public class FixedDepositAccount extends BankAccount {
    private final double annualRate;
    private final int termMonths;
    public FixedDepositAccount(String no, String customerId, double initial, double rate, int months) throws InvalidAmountException {
        super(no, customerId, initial);
        if (months <= 0 || rate < 0) throw new InvalidAmountException("Invalid FD rate or term.");
        this.annualRate = rate; this.termMonths = months;
    }
    public double calculateMaturityAmount() { return getBalance() + getBalance() * annualRate / 100.0 * termMonths / 12.0; }
    @Override public String getAccountType() { return "FIXED DEPOSIT"; }
    @Override public double calculateBenefit() { return calculateMaturityAmount() - getBalance(); }
}