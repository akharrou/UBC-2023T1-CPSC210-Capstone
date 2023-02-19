package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

// https://www.youtube.com/watch?v=JpoI_rdMgLM
public class FinancialAccount {

    private final UUID id;
    private final String creationDatetime;
    private final String firstname;
    private final String lastname;
    private double presentNetCashflow;
    private double targetNetCashflow;
    private FinancialLedger ledger;

    public FinancialAccount() {
        this.id = UUID.randomUUID();
        this.creationDatetime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        this.firstname = "John";
        this.lastname = "Doe";
        this.presentNetCashflow = 0.0;
        this.targetNetCashflow = 0.0;
        this.ledger = new FinancialLedger();
    }

    public FinancialAccount(String first, String last, double targetNetCashflow) {
        this.id = UUID.randomUUID();
        this.creationDatetime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        this.firstname = first;
        this.lastname = last;
        this.presentNetCashflow = 0.0;
        this.targetNetCashflow = targetNetCashflow;
        this.ledger = new FinancialLedger();
    }

    public void addTransaction(double amount, String description) {
        this.ledger.addEntry(amount, description);
        this.presentNetCashflow = this.ledger.getNetCashflow();
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

    // MODIFIES: this
    // EFFECTS: sets account's target net cashflow.
    public void setTargetNetCashflow(double targetNetCashflow) {
        this.targetNetCashflow = targetNetCashflow;
    }

    // EFFECTS: returns a financial summary of this account.
    public String repr() {
        String repr = "\n";

        // header
        repr += "Financial Summary ():\n";
        repr += "\n  Created: " + this.creationDatetime;
        repr += "\n  Account-ID: " + this.id;
        repr += "\n  Owner: " + this.firstname + " " + this.lastname;

        if (this.ledger.getTotalEntries() < 1) {
            repr += "\n  Total Entries: " + this.ledger.getTotalEntries();
            return repr;
        }

        // body
        repr += "\n  Ledger:\n" + this.ledger.repr(2);
        // footer
        repr += String.format("\n  Total Entries: %d", this.ledger.getTotalEntries());
        repr += String.format("\n  Present Net Cashflow: $%,.2f", this.ledger.getNetCashflow());
        repr += String.format("\n  Target Net Cashflow: $%,.2f", this.targetNetCashflow);

        if (this.presentNetCashflow > this.targetNetCashflow) {
            repr += "\n  Financial Standing: above target 🟢";
        } else if (this.presentNetCashflow == this.targetNetCashflow) {
            repr += "\n  Financial Standing: on target 🟠";
        } else {
            repr += "\n  Financial Standing: below target 🔴";
        }

        return repr;
    }
}
