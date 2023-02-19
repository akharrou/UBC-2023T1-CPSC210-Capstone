package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// https://www.youtube.com/watch?v=JpoI_rdMgLM
public class FinancialAccount {

    private final UUID id;
    private final String firstname;
    private final String lastname;
    private double presentNetCashflow;
    private double targetNetCashflow;
    private FinancialLedger ledger;

    public FinancialAccount() {
        this.id = UUID.randomUUID();
        this.firstname = "John";
        this.lastname = "Doe";
        this.presentNetCashflow = 0.0;
        this.targetNetCashflow = 0.0;
        this.ledger = new FinancialLedger();
    }

    public void addTransaction(double amount, String description) {
        this.ledger.addEntry(amount, description);
        this.presentNetCashflow = this.ledger.getNetCashflow();
    }

    // EFFECTS: returns the financial summary of this account.
    public String getSummary() {
        String summary = "\n";

        // header
        summary += "Financial Summary:\n";
        summary += "\n  Datetime: " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        summary += "\n  Account-ID: " + this.id;
        summary += "\n  Owner: " + this.firstname + " " + this.lastname;
        // body
        summary += "\n  Ledger:\n" + this.ledger.repr(2);
        // footer
        summary += "\n  Total Entries: " + this.ledger.getTotalEntries();
        summary += "\n  Present Net Cashflow: " + this.ledger.getNetCashflow();
        summary += "\n  Target Net Cashflow: " + this.targetNetCashflow;

        if (this.presentNetCashflow > this.targetNetCashflow) {
            summary += "\n\nCommentary: You're on target.";
        } else if (this.presentNetCashflow < this.targetNetCashflow) {
            summary += "\n\nCommentary: You're going broke.";
        } else {
            summary += "\n\nCommentary: You're spot on.";
        }

        return summary;
    }

    // EFFECTS: returns account's identifier.
    public UUID getID() {
        return this.id;
    }

    // EFFECTS: returns account's owner firstname.
    public String getFirstname() {
        return this.firstname;
    }

    // EFFECTS: returns account's owner lastname.
    public String getLastname() {
        return this.lastname;
    }

    // EFFECTS: returns account's present net cashflow.
    public double getPresentNetCashflow() {
        return this.presentNetCashflow;
    }

    // EFFECTS: returns account's target net cashflow.
    public double getTargetNetCashflow() {
        return this.targetNetCashflow;
    }
}
