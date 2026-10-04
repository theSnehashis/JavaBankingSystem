# Java Banking Management System --- Level 1

A console-based **Java OOP Banking Management System** designed to
demonstrate practical object-oriented programming concepts through a
complete banking workflow.

The project implements customer authentication, multiple account types,
banking transactions, beneficiary management, fund transfers,
transaction history, interest/maturity calculations, account status
management, custom exceptions, and admin operations.

> **Note:** This Level 1 version uses in-memory Java collections. Data
> is reset when the application exits.

------------------------------------------------------------------------

## ✨ Features

### Customer Features

-   Customer login
-   View own accounts
-   Savings, Current, Student and Fixed Deposit accounts
-   Account status: `ACTIVE`, `BLOCKED`, `CLOSED`
-   Deposit and withdrawal
-   Beneficiary management: add, remove and view
-   Duplicate/invalid beneficiary validation
-   Fund transfer
-   Transaction history
-   Savings interest calculation
-   Fixed Deposit maturity calculation
-   Input and business-rule validation
-   Customer-level account access protection

### Admin Features

-   Admin login
-   List customers
-   List accounts
-   Block an account
-   Activate an account
-   Close an account
-   Create an account for a customer

------------------------------------------------------------------------

## 🧩 OOP Concepts Demonstrated

-   **Encapsulation** --- controlled access to customer and account data
-   **Abstraction** --- common banking behavior represented through base
    classes
-   **Inheritance** --- specialized account types extend the common
    account model
-   **Polymorphism** --- account-specific behavior is handled through
    common base types
-   **Collections** --- Java collections for in-memory data management
-   **Enums** --- account status and transaction types
-   **Custom Exceptions** --- domain-specific error handling
-   **Service Layer** --- banking and authentication logic separated
    from the console entry point

------------------------------------------------------------------------

## 💸 Transaction Types

-   `DEPOSIT`
-   `WITHDRAW`
-   `TRANSFER_IN`
-   `TRANSFER_OUT`
-   `INTEREST`
-   `FD_MATURITY`

Fund transfers create dedicated `TRANSFER_OUT` and `TRANSFER_IN` records
for the sender and receiver.

------------------------------------------------------------------------

## 🔐 Access & Validation

The application includes:

-   Invalid login handling
-   Invalid account handling
-   Invalid amount validation
-   Insufficient balance protection
-   Blocked/closed account restrictions
-   Duplicate beneficiary prevention
-   Invalid beneficiary prevention
-   Own-account beneficiary validation
-   Customer-level account access restriction
-   Transaction history restricted to the logged-in customer's accounts

------------------------------------------------------------------------

## 🗂️ Project Structure

``` text
JavaBankingSystem_Level1/
├── README.md
├── .gitignore
├── .project
├── .classpath
└── src/
    └── com/
        └── resultflow/
            └── banking/
                ├── Main.java
                ├── model/
                │   ├── Customer.java
                │   ├── BankAccount.java
                │   ├── SavingsAccount.java
                │   ├── CurrentAccount.java
                │   ├── StudentAccount.java
                │   ├── FixedDepositAccount.java
                │   ├── Transaction.java
                │   └── TransactionType.java
                ├── exception/
                │   ├── BankingException.java
                │   ├── AccountNotFoundException.java
                │   ├── AuthenticationException.java
                │   ├── InsufficientBalanceException.java
                │   ├── InvalidAccountException.java
                │   └── InvalidAmountException.java
                ├── service/
                │   ├── BankingService.java
                │   └── AuthenticationService.java
                └── util/
                    ├── InputUtil.java
                    └── IdGenerator.java
```

------------------------------------------------------------------------

## ⚙️ Requirements

-   **Java 17 or later**
-   Eclipse IDE or any Java IDE
-   Command Prompt/Terminal

No external database or third-party framework is required for this Level
1 version.

------------------------------------------------------------------------

## ▶️ How to Run

### Eclipse

1.  Open Eclipse.
2.  Import the project as an existing Java project.
3.  Make sure the project uses **Java 17+**.
4.  Open:

``` text
src/com/resultflow/banking/Main.java
```

5.  Run `Main.java` as a **Java Application**.
6.  Use the demo credentials below.

### Command Line

From the project directory, compile the source files and run:

``` bash
java com.resultflow.banking.Main
```

All interaction takes place through the terminal/Eclipse Console.

------------------------------------------------------------------------

## 🔑 Demo Credentials

### Customer 1

``` text
Username: demo
Password: demo123

Accounts:
S10001 — Savings
C10001 — Current
```

### Customer 2

``` text
Username: test
Password: test123

Account:
S10002 — Savings
```

### Admin

``` text
Username: admin
Password: admin123
```

> These credentials are demo data for the in-memory application.

------------------------------------------------------------------------

## 💸 Quick Fund Transfer Test

The second customer/account is included so the transfer workflow can be
tested immediately.

1.  Login as `demo` / `demo123`.
2.  Open **Beneficiaries**.
3.  Add `S10002`.
4.  Open **Transfer Funds**.
5.  Select `S10001` as the sender.
6.  Select `S10002` as the beneficiary.
7.  Enter a positive amount within the sender's available balance.

Expected result:

``` text
Transfer successful.
```

The system records:

``` text
TRANSFER_OUT → S10001
TRANSFER_IN  → S10002
```

------------------------------------------------------------------------

## 🧪 Recommended Test Flow

1.  Customer login
2.  View accounts
3.  Deposit
4.  Withdraw
5.  View transaction history
6.  Add a beneficiary
7.  Test duplicate beneficiary validation
8.  Transfer funds
9.  Login as the receiving customer
10. Verify `TRANSFER_IN`
11. Calculate savings interest
12. Login as admin
13. List customers
14. List accounts
15. Block an account
16. Activate the account
17. Create an account
18. Close an account

------------------------------------------------------------------------

## 🔒 Account Access Protection

Customer operations are restricted to accounts owned by the currently
logged-in customer.

When logged in as `demo`, the customer can operate on:

``` text
S10001
C10001
```

`S10002` belongs to the `test` customer and cannot be accessed directly
by `demo`.

It can, however, be registered as a beneficiary for fund transfers.

------------------------------------------------------------------------

## 🏗️ Architecture

``` text
User
 │
 ▼
Main / Console UI
 │
 ▼
AuthenticationService
 │
 ▼
BankingService
 │
 ├── Customer
 ├── BankAccount
 │    ├── SavingsAccount
 │    ├── CurrentAccount
 │    ├── StudentAccount
 │    └── FixedDepositAccount
 │
 ├── Transaction
 └── Custom Exceptions
```

The structure separates user interaction, authentication, banking
operations, domain models, and error handling.

------------------------------------------------------------------------

## 🛠️ Technology Stack

-   **Java 17+**
-   **Object-Oriented Programming**
-   **Java Collections Framework**
-   **Custom Exception Handling**
-   **Enums**
-   **Eclipse IDE**
-   **Git / GitHub**

------------------------------------------------------------------------

## 📌 Data Storage

This Level 1 implementation intentionally uses **in-memory Java
collections** instead of a database.

Therefore:

-   No database setup is required.
-   No external server is required.
-   Data exists only while the application is running.
-   All data resets when the application exits.

A future version can introduce persistent storage using **JDBC + MySQL**
and a DAO/repository layer.

------------------------------------------------------------------------

## 🚀 Future Enhancements

-   JDBC + MySQL persistence
-   DAO/Repository layer
-   Password hashing
-   Persistent customer registration
-   Database-backed transaction history
-   REST API
-   Web or mobile frontend
-   Role-based authorization
-   Automated unit/integration testing

------------------------------------------------------------------------

## 📄 Project Summary

**Java Banking Management System --- Level 1** is a practical console
application demonstrating how Java OOP concepts can be combined to build
a structured banking workflow.

### Core Workflow

**Authentication → Account Management → Transactions → Beneficiaries →
Fund Transfer → Transaction History → Interest/Maturity → Admin
Management**

------------------------------------------------------------------------

## 👨‍💻 Author

**Snehashis Dalui**

BCA Student | Aspiring Software Engineer
