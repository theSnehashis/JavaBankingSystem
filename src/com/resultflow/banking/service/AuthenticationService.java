package com.resultflow.banking.service;
import com.resultflow.banking.exception.AuthenticationException;
import com.resultflow.banking.model.Customer;
public class AuthenticationService {
    private final BankingService bank;
    public AuthenticationService(BankingService bank) { this.bank = bank; }
    public Customer loginCustomer(String username, String password) throws AuthenticationException {
        Customer c = bank.findCustomer(username);
        if (c == null || !c.getPassword().equals(password)) throw new AuthenticationException("Invalid customer credentials.");
        return c;
    }
    public void loginAdmin(String username, String password) throws AuthenticationException {
        if (!"admin".equals(username) || !"admin123".equals(password)) throw new AuthenticationException("Invalid admin credentials.");
    }
}