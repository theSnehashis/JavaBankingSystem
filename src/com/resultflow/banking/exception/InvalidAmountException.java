package com.resultflow.banking.exception;
public class InvalidAmountException extends BankingException {
    private static final long serialVersionUID = 1L; public InvalidAmountException(String m) { super(m); } }