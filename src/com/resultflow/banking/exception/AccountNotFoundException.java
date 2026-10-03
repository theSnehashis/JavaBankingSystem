package com.resultflow.banking.exception;
public class AccountNotFoundException extends BankingException {
    private static final long serialVersionUID = 1L; public AccountNotFoundException(String m) { super(m); } }