# UBC 2022 T2 CPSC 210 - Personal Project

## About

1. **What will the application do?**

    *The application will be a budget tracker. You can add receipts, and will tell you if you've stayed under your budget (and how much left you have to spend),
    have met your budget, or are over it (and how much you need to reduce).*

1. **Who will use it?**

    *Me, students, and people on tight budgets.*

1. **Why is this project of interest to you?**

    *Because on a tight budget, must make sure I don't go broke and starve.*

## Phase 01-02: User Stories

- Phase 01
    - [x] As a user, I want to be able to set a financial goal.
    - [x] As a user, I want to be able to edit my financial goal.
    - [x] As a user, I want to be able to add financial ( inflow | outflow ) entries to my financial ledger.
    - [x] As a user, I want to be able to delete all financial entries from my financial ledger.
    - [x] As a user, I want to be able to get a listing of my financial ledger.
    - [x] As a user, I want to be able to get statistics on my financial account and ledger.
    - [x] As a user, I want to be able to get a summary and financial standing report on my financial account.
- Phase 02
    - [x] As a user, when I logout, I want to be reminded and have the optional ablility to save my account data information and ledger to disk
    - [x] As a user, when I start the application, I want the option to [login and] load my [previously saved] financial account and ledger data information from file.
    - [x] As a user, I want be able to create more than one account, for all of my friends and family [and similarly have all the above features]

## Phase 03: GUI Instructions for Grader

- You can generate the first required action (adding Xs to Ys) by:
    1. launching the application
    1. registering
    1. clicking "Add"
    1. filling in the popup dialog form
    1. clicking "Ok"
- You can generate the second required action (filtering & sorting) by doing one of:
    - typing a search query; filters on description field with given query
    - selecting one of the available drop down menu items; filters by entry type ( all | inflow | outflow )
    - clicking one of the table headers; sorts by clicked header field
- You can locate my visual component by starting the app; it's on the launch screen.
- You can save the state of my application by [from the account screen] clicking "logout" or closing the window, and then clicking "yes" to the save popup.
- You can reload the state of my application by [from the login/register screen] loggining in

## Phase 4: Event logging (Task 2)

Representative sample of logged events:

```plaintext
. . .
[Sat Apr 01 07:20:09 PDT 2023] [FinancialLedger] new empty financial ledger constructed
[Sat Apr 01 07:20:09 PDT 2023] [FinancialAccount] new fresh financial account constructed
[Sat Apr 01 07:20:09 PDT 2023] [FinancialAccount.setTargetNetCashflow] net target cashflow set to: '89.0'
[Sat Apr 01 07:20:14 PDT 2023] [FinancialEntry] new inflow financial entry constructed
[Sat Apr 01 07:20:14 PDT 2023] [FinancialLedger.addEntry] added new financial entry to ledger
[Sat Apr 01 07:20:14 PDT 2023] [FinancialAccount.recordFinancialEntry] new financial entry registered
[Sat Apr 01 07:20:17 PDT 2023] [FinancialEntry] new inflow financial entry constructed
[Sat Apr 01 07:20:17 PDT 2023] [FinancialLedger.addEntry] added new financial entry to ledger
[Sat Apr 01 07:20:17 PDT 2023] [FinancialAccount.recordFinancialEntry] new financial entry registered
[Sat Apr 01 07:20:21 PDT 2023] [FinancialEntry] new outflow financial entry constructed
[Sat Apr 01 07:20:21 PDT 2023] [FinancialLedger.addEntry] added new financial entry to ledger
[Sat Apr 01 07:20:21 PDT 2023] [FinancialAccount.recordFinancialEntry] new financial entry registered
[Sat Apr 01 07:20:24 PDT 2023] [FinancialEntry] new outflow financial entry constructed
[Sat Apr 01 07:20:24 PDT 2023] [FinancialLedger.addEntry] added new financial entry to ledger
[Sat Apr 01 07:20:24 PDT 2023] [FinancialAccount.recordFinancialEntry] new financial entry registered
[Sat Apr 01 07:20:29 PDT 2023] [FinancialAccount.setTargetNetCashflow] net target cashflow set to: '100.0'
[Sat Apr 01 07:20:32 PDT 2023] [FinancialEntry] new inflow financial entry constructed
[Sat Apr 01 07:20:32 PDT 2023] [FinancialLedger.addEntry] added new financial entry to ledger
[Sat Apr 01 07:20:32 PDT 2023] [FinancialAccount.recordFinancialEntry] new financial entry registered
[Sat Apr 01 07:20:35 PDT 2023] [FinancialEntry] new outflow financial entry constructed
[Sat Apr 01 07:20:35 PDT 2023] [FinancialLedger.addEntry] added new financial entry to ledger
[Sat Apr 01 07:20:35 PDT 2023] [FinancialAccount.recordFinancialEntry] new financial entry registered
. . .
```

<!--
## Domain Analysis

- [x] What information is changing and what is constant? will need:
    - constant
        1. first and last name of user
    - changing
        1. financial goals list
        1. financial ledger (where financial activity is recorded and maintained)
        1. assets list (reoccurring inflows)
        1. liabilities list (reoccurring outflows)
        1. current financial standing
        1. expected future financial standing
        1. archive of generated financial statements
- [x] Identify the different types of data that you will need to represent the information in your domain.
    - [x] a financial `Account` class/abstraction to hold all financial account information of the user
        - [x] a list field to hold financial goals
            - [x] a financial `Goal` class/abstraction to represent a users financial goals
        - [x] a `Ledger` list field to hold all financial activity entries (e.g. inflow and outflow payments)
            - [x] a financial activity `Entry` class/abstraction to represent an entry to the ledger
            - [x] a financial `Asset` class/abstraction to represent a reoccuring money inflow generating financial item
            - [x] a financial `Liability` class/abstraction to represent a reoccuring money outflow generating financial item
        - [x] a `FinancialStatement` class/abstraction to represent a snapshot of the users financial standing at some specified date in time
        - [x] an archive list of previously generated financial statements
        - [x] a financial `Log` of all financial account [app] activities
- [x] Identify methods that classes must have to be able to provide functionality described in your user stories
