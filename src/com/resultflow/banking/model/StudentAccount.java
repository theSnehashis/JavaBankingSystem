package com.resultflow.banking.model;
import com.resultflow.banking.exception.InvalidAmountException;
public class StudentAccount extends SavingsAccount {
    public StudentAccount(String no, String customerId, double initial) throws InvalidAmountException { super(no, customerId, initial, 5.0); }
    @Override public String getAccountType() { return "STUDENT"; }
}