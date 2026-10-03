# Java Banking System — Level 1 Upgrade

A console-based Java OOP banking application covering the Level 1 feature set.

## Features
- Customer registration and login
- Savings, Current, Student and Fixed Deposit accounts
- Account status: ACTIVE, BLOCKED, CLOSED
- Deposit and withdrawal
- Beneficiary management
- Fund transfer
- Transaction history with transaction types
- Savings interest calculation
- Fixed Deposit maturity calculation
- Custom exception handling
- In-memory storage using Java collections

## Demo login
Customer 1: `demo` / `demo123` (accounts: S10001, C10001)
Customer 2: `test` / `test123` (account: S10002)
Admin: `admin` / `admin123`

## Run
Use Java 17+ and run `com.resultflow.banking.Main`.
Data is intentionally in-memory and resets when the application exits.


## Level 1 transfer test
Login as `demo`, open Beneficiaries, add `S10002`, then use Transfer Funds:
`S10001` → `S10002` → any positive amount within the sender balance.

The second demo account exists specifically so beneficiary and transfer features
can be tested immediately without creating another account first.


## Final Level 1 test accounts

**Customer 1**
- Username: `demo`
- Password: `demo123`
- Accounts: `S10001`, `C10001`

**Customer 2**
- Username: `test`
- Password: `test123`
- Account: `S10002`

**Admin**
- Username: `admin`
- Password: `admin123`

`S10002` is owned by the second demo customer so beneficiary and transfer
features can be tested immediately. Account operations and transaction
history are restricted to accounts owned by the logged-in customer.
