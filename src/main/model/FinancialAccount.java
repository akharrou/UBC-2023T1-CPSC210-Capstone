package model;

// https://www.youtube.com/watch?v=JpoI_rdMgLM
public class FinancialAccount {

    // private List<FinancialGoal> goals;
    // private List<FinancialStatement> archive;
    // private List<LogEntry> log;
    private String userFirstname;
    private String userLastname;
    private FinancialLedger userLedger;

    private void createAccount() {
        // "Firstname: "
        // "Lastname: "
        // "Budget: "
        // "Welcome " + userFirstname + " " + userLastname ". You're currently on budget !"
    }

    private void addFinancialGoal();
    private void getFinancialGoal();
    private void lstFinancialGoal();
    private void edtFinancialGoal();
    private void delFinancialGoal();

    private void addFinancialAsset();
    private void getFinancialAsset();
    private void lstFinancialAsset();
    private void edtFinancialAsset();
    private void delFinancialAsset();

    private void addFinancialLiability();
    private void getFinancialLiability();
    private void lstFinancialLiability();
    private void edtFinancialLiability();
    private void delFinancialLiability();

    private void generateFinancialStatementReportForDate();
    private void simulateFinancialActivityTillDate();
    private void generateFinancialGoalsReportForDate();
}

class FinancialLedger {

    private void lstAll();
    private void delAll();

    private void addFinancialInflow();
    private void getFinancialInflow();
    private void lstFinancialInflow();
    private void edtFinancialInflow();
    private void delFinancialInflow();

    private void addFinancialOutflow();
    private void getFinancialOutflow();
    private void lstFinancialOutflow();
    private void edtFinancialOutflow();
    private void delFinancialOutflow();
}

class FinancialLedgerEntry {
    // stub
}

class FinancialGoal {
    // stub
}

class FinancialCashflow {
    // stub
}

class FinancialAsset implements FinancialCashflow {
    // stub
}

class FinancialLiability implements FinancialCashflow {
    // stub
}

class FinancialStatement {
    // stub
}

class LogEntry {
    // stub
}
