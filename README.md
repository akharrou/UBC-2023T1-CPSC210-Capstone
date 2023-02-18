# UBC 2022 T2 CPSC 210 - Personal Project

## About

1. **What will the application do?**

    *The application will be a budget tracker. You can add receipts, and will tell you if you've stayed under your budget (and how much left you have to spend),
    have met your budget, or are over it (and how much you need to reduce).*

1. **Who will use it?**

    *Me, students, and people on tight budgets.*

1. **Why is this project of interest to you?**

    *Because on a tight budget, must make sure I don't go broke and starve.*

## User Stories

- [ ] As a user, I want to be able to ( add | list | edit | delete ) my financial goals ( to | from | from | from ) my financial goals list
    - [ ] `account.(add|get|lst|edt|del)FinancialGoal`
- [ ] As a user, I want to be able to ( add | list | edit | delete ) financial entries ( to | from | from | from ) my financial ledger
    - [ ] As a user, I want to be able to ( add | list | edit | delete ) assets (expected reoccuring money inflows) ( to | from | from | from ) my account assets list <!-- (e.g. income, scholarship, student loan, financial aid, parent monthly contribution); these will automatically generate and add inflow payments to my financial ledger -->
        - [ ] `account.(add|get|lst|edt|del)FinancialAsset` <!-- add amount to be added + reoccurrance pattern -->
    - [ ] As a user, I want to be able to ( add | list | edit | delete ) liabilities (expected reoccuring money outflows) ( to | from | from | from ) my account liabilities list <!-- (e.g. daily, weekly, monthly, quarterly, semi-annual, annual, expenses; rent, wifi plan, cellular plan, amazon prime, netflix); these will automatically generate and add outflow payments to my financial ledger -->
        - [ ] `account.(add|get|lst|edt|del)FinancialLiability` <!-- add amount to be deducted + reoccurrance pattern -->
    - [ ] As a user, I want to be able to ( add | list | edit | delete ) one-time inflows <!-- (e.g. friend pays you 5 bucks cause X, you win the lotto) to my financial ledger -->
        - [ ] `ledger.(add|get|lst|edt|del)FinancialInflow` <!-- for a specified date -->
    - [ ] As a user, I want to be able to ( add | list | edit | delete ) one-time outflows <!-- (e.g. parking fee, late payment fee, made an unexpected grocery, unexpected doctor’s appointment, paid for car crash damages) to my financial ledger -->
        - [ ] `ledger.(add|get|lst|edt|del)FinancialOutflow` <!-- for a specified date -->
- [ ] As a user, I want to be able to simulate and get a financial statement report [for some specified date]
    - [ ] `account.generateFinancialStatementReport` <!-- generate financial statement for some specified date -->
        - [ ] `account.simulateFinancialActivityTillDate` <!-- simulate financial activity till specified date -->
    - [ ] `account.generateFinancialGoalsReport` <!-- given a financial statement report, check and mark which financial goals have been met, and which that haven't -->
    - [ ] `account.printFinancialStatement` <!-- display financial statement report -->

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
