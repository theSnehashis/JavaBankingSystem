package com.resultflow.banking;

import com.resultflow.banking.exception.*;
import com.resultflow.banking.model.*;
import com.resultflow.banking.service.BankingService;
import com.resultflow.banking.service.AuthenticationService;
import com.resultflow.banking.util.InputUtil;

import java.util.List;

public class Main {
    private static final BankingService bank = new BankingService();
    private static final AuthenticationService auth = new AuthenticationService(bank);

    public static void main(String[] args) throws BankingException {
        seedDemo();
        System.out.println("\n==============================================");
        System.out.println("        RESULTFLOW JAVA BANKING SYSTEM");
        System.out.println("==============================================");
        while (true) {
            System.out.println("\n1. Customer Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            int choice = InputUtil.readInt("Choose: ");
            try {
                if (choice == 1) customerLogin();
                else if (choice == 2) adminLogin();
                else if (choice == 3) { System.out.println("Goodbye!"); return; }
                else System.out.println("Invalid choice.");
            } catch (BankingException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void seedDemo() throws BankingException {
        if (bank.findCustomer("demo") == null) {
            Customer c = new Customer("C1001", "Demo Customer", "demo", "demo123");
            bank.addCustomer(c);
            SavingsAccount s = new SavingsAccount("S10001", c.getCustomerId(), 5000, 4.0);
            CurrentAccount cur = new CurrentAccount("C10001", c.getCustomerId(), 10000, 2000);
            bank.addAccount(s); bank.addAccount(cur);
            c.addAccountNumber(s.getAccountNumber());
            c.addAccountNumber(cur.getAccountNumber());
            s.deposit(500, "Initial demo deposit");
        }

        // Second demo customer so beneficiary and fund-transfer features
        // are immediately testable after a fresh application start.
        if (bank.findCustomer("test") == null) {
            Customer c2 = new Customer("C1002", "Test Customer", "test", "test123");
            bank.addCustomer(c2);
            SavingsAccount s2 = new SavingsAccount("S10002", c2.getCustomerId(), 5000, 4.0);
            bank.addAccount(s2);
            c2.addAccountNumber(s2.getAccountNumber());
        }
    }

    private static void customerLogin() throws BankingException {
        String u = InputUtil.readLine("Username: ");
        String p = InputUtil.readLine("Password: ");
        Customer c = auth.loginCustomer(u, p);
        System.out.println("\nWelcome, " + c.getName() + "!");
        customerMenu(c);
    }

    private static void customerMenu(Customer c) {
        while (true) {
            System.out.println("\n--- CUSTOMER MENU ---");
            System.out.println("1. My Accounts");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Beneficiaries");
            System.out.println("6. Transaction History");
            System.out.println("7. Calculate Interest / Maturity");
            System.out.println("8. Logout");
            int choice = InputUtil.readInt("Choose: ");
            try {
                switch (choice) {
                    case 1 -> showAccounts(c);
                    case 2 -> deposit(c);
                    case 3 -> withdraw(c);
                    case 4 -> transfer(c);
                    case 5 -> beneficiaryMenu(c);
                    case 6 -> transactions(c);
                    case 7 -> financialCalculation(c);
                    case 8 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (BankingException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void showAccounts(Customer c) {
        System.out.println("\n--- MY ACCOUNTS ---");
        for (String no : c.getAccountNumbers()) {
            BankAccount a = bank.findAccount(no);
            System.out.println(a);
        }
        if ("demo".equalsIgnoreCase(c.getUsername())) {
            System.out.println("Transfer test account: S10002 (test / test123)");
        }
    }

    private static String chooseOwnAccount(Customer c) throws AccountNotFoundException {
        showAccounts(c);
        String no = InputUtil.readLine("Account number: ");
        BankAccount a = bank.findAccount(no);
        if (a == null || !c.owns(no)) throw new AccountNotFoundException("Account not found in your profile.");
        return no;
    }

    private static void deposit(Customer c) throws BankingException {
        String no = chooseOwnAccount(c);
        double amount = InputUtil.readDouble("Amount: ");
        bank.deposit(no, amount, "Cash deposit");
        System.out.println("Deposit successful.");
    }

    private static void withdraw(Customer c) throws BankingException {
        String no = chooseOwnAccount(c);
        double amount = InputUtil.readDouble("Amount: ");
        bank.withdraw(no, amount, "Cash withdrawal");
        System.out.println("Withdrawal successful.");
    }

    private static void transfer(Customer c) throws BankingException {
        String from = chooseOwnAccount(c);
        String beneficiary = InputUtil.readLine("Beneficiary account: ");
        if (!c.hasBeneficiary(beneficiary)) throw new InvalidAccountException("Add this account as a beneficiary first.");
        double amount = InputUtil.readDouble("Amount: ");
        bank.transfer(from, beneficiary, amount);
        System.out.println("Transfer successful.");
    }

    private static void beneficiaryMenu(Customer c) {
        while (true) {
            System.out.println("\n--- BENEFICIARIES ---");
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. View");
            System.out.println("4. Back");
            int ch = InputUtil.readInt("Choose: ");
            try {
                if (ch == 1) {
                    String account = InputUtil.readLine("Account number: ");
                    bank.validateBeneficiary(c, account);
                    c.addBeneficiary(account);
                    System.out.println("Beneficiary added.");
                } else if (ch == 2) {
                    String account = InputUtil.readLine("Account number: ");
                    c.removeBeneficiary(account);
                    System.out.println("Beneficiary removed.");
                } else if (ch == 3) {
                    System.out.println(c.getBeneficiaries());
                } else if (ch == 4) return;
                else System.out.println("Invalid choice.");
            } catch (BankingException e) { System.out.println("ERROR: " + e.getMessage()); }
        }
    }

    private static void transactions(Customer c) throws BankingException {
        System.out.println("\nSelect one of YOUR accounts for transaction history.");
        String no = chooseOwnAccount(c);
        List<Transaction> list = bank.getTransactions(no);
        if (list.isEmpty()) System.out.println("No transactions for this account.");
        else list.forEach(System.out::println);
    }

    private static void financialCalculation(Customer c) throws BankingException {
        String no = chooseOwnAccount(c);
        BankAccount a = bank.findAccount(no);
        if (a instanceof SavingsAccount s)
            System.out.printf("Estimated annual interest: %.2f%n", s.calculateInterest());
        else if (a instanceof FixedDepositAccount fd)
            System.out.printf("Maturity amount: %.2f%n", fd.calculateMaturityAmount());
        else System.out.println("This account type has no interest/maturity calculation.");
    }

    private static void adminLogin() throws BankingException {
        String u = InputUtil.readLine("Admin username: ");
        String p = InputUtil.readLine("Admin password: ");
        auth.loginAdmin(u, p);
        System.out.println("Admin login successful.");
        adminMenu();
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- ADMIN MENU ---");
            System.out.println("1. List customers");
            System.out.println("2. List accounts");
            System.out.println("3. Block account");
            System.out.println("4. Activate account");
            System.out.println("5. Close account");
            System.out.println("6. Create account for customer");
            System.out.println("7. Logout");
            int ch = InputUtil.readInt("Choose: ");
            try {
                switch (ch) {
                    case 1 -> bank.getCustomers().forEach(System.out::println);
                    case 2 -> bank.getAccounts().forEach(System.out::println);
                    case 3 -> changeStatus(AccountStatus.BLOCKED);
                    case 4 -> changeStatus(AccountStatus.ACTIVE);
                    case 5 -> changeStatus(AccountStatus.CLOSED);
                    case 6 -> createAccount();
                    case 7 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (BankingException e) { System.out.println("ERROR: " + e.getMessage()); }
        }
    }

    private static void changeStatus(AccountStatus status) throws BankingException {
        String no = InputUtil.readLine("Account number: ");
        bank.changeStatus(no, status);
        System.out.println("Account status changed to " + status + ".");
    }

    private static void createAccount() throws BankingException {
        String customerId = InputUtil.readLine("Customer ID: ");
        String type = InputUtil.readLine("Type (SAVINGS/CURRENT/STUDENT/FD): ").toUpperCase();
        double initial = InputUtil.readDouble("Initial deposit: ");
        BankAccount a = bank.createAccountForCustomer(customerId, type, initial);
        System.out.println("Created: " + a);
    }
}