# UBC 2023 (2022 T2) CPSC 210 - Personal Project

Basic financial tracking app.

> Application that tracks financial inflows and outflows, i.e receipts and payments. You state a desired target net cashflow, then input any payments or receipts, then you can view, filter, sort, search through them in a table, and lastly you’re informed about your financial standing, good if your current net cashflow > target net cashflow, bad otherwise.

<center style="display:grid;place-items:center;grid-template-areas:'a b';grid-template-columns:45% 45%;grid-column-gap:1em;margin:1em">
    <div>
        <h3>Register</h3>
        <img src="assets/register.mp4.gif" alt="register" style="float:none;width:100%"></h3>
    </div>
    <div>
        <h3>Login</h3>
        <img src="assets/login.mov.gif" alt="login" style="float:none;width:100%">
    </div>
</center>

<center style="display:grid;place-items:center;grid-template-areas:'a b';grid-template-columns:45% 45%;grid-column-gap:1em;margin:1em">
    <div>
        <h3>Filter & Sort</h3>
        <img src="assets/filter-and-sort.mov.gif" alt="filter-and-sort" style="float:none;width:100%">
    </div>
    <div>
        <h3>Search</h3>
        <img src="assets/search.mp4.gif" alt="search" style="float:none;width:100%">
    </div>
</center>

<center style="display:grid;place-items:center;grid-template-areas:'a b';grid-template-columns:45% 45%;grid-column-gap:1em;margin:1em">
    <div>
        <h3>Add Entries</h3>
        <img src="assets/add.mov.gif" alt="add-entries" style="float:none;width:100%">
    </div>
    <div>
        <h3>Clear, Logout</h3>
        <img src="assets/clear-and-logout.mov.gif" alt="clear-and-logout" style="float:none;width:100%">
    </div>
</center>


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

## Phase 04: Event logging (Task 2)

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

## Phase 04: UML Class Diagram (Task 3)

![UML_Design_Diagram](./UML_Design_Diagram.jpeg)

## Phase 04: Reflection (Task 3)

- If you had more time to work on the project, what substantive refactoring might you use to improve your design?

    <b>increase cohesion & decrease coupling</b>

    In the UI especially, I made decisions that can definitely be improved on. For instance decisions regarding the cohesiveness and level of coupling of certain classes. <br><br>For example: for the `GuiApp` class, I ended up having it take care of both: (1) handling the application's business logic (i.e giving it access to the model and methods to interact with the model based on user input), and (2) coordinating the multi-window GUI application logic (i.e it has fields giving it access to and the ability to change different GUI screens and call their methods, etc). Worse yet, via global package access to the `GUIAapp`, application screens (eg. `LaunchScreen`, `LoginScreen`, and `AccountScreen` classes) could directly access and modify the model and other screens, as a result coupling lots of classes tightly to each other. <br><br> To improve on this, one idea would be to refactor the codebase by separating the concerns of managing the business logic and managing the multi-window GUI screens of the application. for instance, there could be a separate class taking on the role of managing the business logic (eg. `GuiAppLogic`), and another separate class for managing the multi-window GUI application logic (eg. `GuiAppManager`). then perhaps have a well-defined interface that `GuiAppLogic` implements, and another that `GuiAppManager` implements, through which screen classes would be able to interact to trigger business logic related actions (eg. logging-in, registering a new account, loading and saving account data, etc), and GUI app screen transitions, without the need to know anything about the model (i.e the financial account), nor any other screen in the application. <br><br>Classes would now only interact with other classes through well-defined interfaces, and overall as a result, the classes would be more cohesive, less coupled, and the codebase more modular, easier to maintain and extend in the future.

    - other less substantive refactorings:

        1. <b>improving code readability</b>: on many occaisons I might have condensed, inlined, certain expressions (eg. lambdas, big returns, ternary conditionals). I would refactor this by breaking up such instances of code into one or more separate more readable line(s), variable declarations, and/or methods altogether. here the trade-off was getting more done in small amount of space, at the cost readability; not great. for example:

            1. [FinancialLedger public constructor](https://github.students.cs.ubc.ca/CPSC210-2022W-T2/project_b3h9o/blob/main/src/main/model/FinancialLedger.java#L30)
            2. [FinancialLedger's consoleReprInflows and consoleReprOutflows method](https://github.students.cs.ubc.ca/CPSC210-2022W-T2/project_b3h9o/blob/main/src/main/model/FinancialLedger.java#L167)
            3. [AccountScreen applyTableFilter method](https://github.students.cs.ubc.ca/CPSC210-2022W-T2/project_b3h9o/blob/main/src/main/ui/AccountScreen.java#L277) <br>

        1. <b>simplifying implementation</b>: in some situations I think I perhaps may have overcomplicated things. for example:
            1. some might argue that it's not all that necessary to have a separate class for positive financial entries and one for negative financial entries. I might refactor this by using the sign of the financial entry amount value as an indicator of the type of the financial entry, and use conditionals throughout the implementation of `FinancialEntry`'s methods to differentiate and perform the appropriate distinct behaviors. the trade-off here was intuitive quick unplanned implementation, rather than a planned thoughtout, efficient one. although some may argue, that with two separate classes for each it's better in terms of cohesion, modularity, extensibility, i.e it would be easier, cleaner, simpler, etc, to separate out each type's specific behavior, where it belongs, as opposed to having to make `FinancialEntry` more complicated; I think for a more complicated program it would be a good idea to stick to two separate classes, but perhaps unnecessary for simple ones like this program.
            2. for printing out string representations of model classes, I could have taken the simpler more elegant template string approach, rather than concatenating a series of strings, and having conditionals throughout. the trade-off here was speed and ease of the solution over a proper simple elegant readable one.

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


    1. <b>decrease coupling</b>: I think I might of coupled parts of the UI more than necessary. this is bad because as a result maintenance effort increases, flexibility decreases, reusability decreases, and risk of regressions increases; but we want the opposite. for example:
        1. the `GuiApp` class and the `AccountScreen` class are tightly coupled, as a result changing one likely requires changing the other; that's not good.

the AccountScreen, LoginScreen, LaunchScreen don't need to know anything about other screens, and even less about the financial account data.
having well defined transition methods for handling what happens when buttons, that transition the user from one screen to the other, are clicked and calling those from the different screens would decrease coupling, and increase cohesion.

screens call transition methods from GuiAppManager class and update model via GuiAppLogic app.

An even fuller refactoring might include making a fully UI-independent `AppManager` class and `AppLogic` class, the former managing application screens and transitions between them, and the latter handling application business logic (eg. registering, logging-in, triggering updates in the model). then I would have a `GuiAppManager` and `TuiAppManager` extend `AppManager`, and `GuiAppLogic` and `TuiAppLogic` extend `AppLogic`.

App
the classes extending `AppManager` would be the interface through which screens would be launched, and transitioned between.
the classes extending `AppLogic` would be the interface through which the model would would be updated based on the UI state and vice versa.
`Tui` prefixed classes would interface with console (`System.out`) methods to achieve this, and `Gui` prefixed classes would interface with GUI (`swing`) methods to achieve this.
