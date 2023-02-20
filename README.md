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

- [x] As a user, I want to be able to have, consult, and edit my financial (net cashflow) goal
- [x] As a user, I want to be able to ( add | list ) financial entries ( to | from | from | from ) my financial ledger
    - [x] As a user, I want to be able to ( add | list ) one-time inflows <!-- (e.g. friend pays you 5 bucks cause X, you win the lotto) to my financial ledger -->
    - [x] As a user, I want to be able to ( add | list ) one-time outflows <!-- (e.g. parking fee, late payment fee, made an unexpected grocery, unexpected doctor’s appointment, paid for car crash damages) to my financial ledger -->
- [x] As a user, I want to be able to get a financial summary report
- [ ] As a user, when I select the quit option from the application menu, I want to be reminded and have the optional ablility to save my account data information and ledger to file
- [ ] As a user, when I start the application, I want to be able I want to have the option to [login and] load my [previously saved] financial account and ledger data information from file.

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
