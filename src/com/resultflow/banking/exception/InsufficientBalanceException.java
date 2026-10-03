package com.resultflow.banking.exception;
public class InsufficientBalanceException extends BankingException {
    private static final long serialVersionUID = 1L; public InsufficientBalanceException(String m) { super(m); } }