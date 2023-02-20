package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.json.JSONObject;

// Represents the financial account of some user. Holds information about the user,
//   and crucially a financial ledger of all of a user's financial inflows and outflows.
public class FinancialAccount
        implements Writable<JSONObject> {

    private final UUID id;
    private final String created;
    private final String firstname;
    private final String lastname;
    private double presentNetCashflow;
    private double targetNetCashflow;
    private FinancialLedger ledger;

    // EFFECTS: creates a new financial account populated with given input info.
    public FinancialAccount(String first, String last, double targetNetCashflow) {
        this.id = UUID.randomUUID();
        this.created = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        this.firstname = first;
        this.lastname = last;
        this.presentNetCashflow = 0.0;
        this.targetNetCashflow = targetNetCashflow;
        this.ledger = new FinancialLedger();
    }

    // EFFECTS: recreates the financial account represented by the JSON object.
    public FinancialAccount(JSONObject account) {
        this.id = UUID.fromString(account.getString("id"));
        this.created = account.getString("created");
        this.firstname = account.getString("firstname");
        this.lastname = account.getString("lastname");
        this.presentNetCashflow = account.getDouble("presentNetCashflow");
        this.targetNetCashflow = account.getDouble("targetNetCashflow");
        this.ledger = new FinancialLedger(account.getJSONArray("ledger"));
    }

    // MODIFIES: this
    // EFFECTS: adds a new financial entry to this accounts ledger and updates account information.
    //          if no exceptions are thrown, returns true, indicating successful operation.
    public boolean recordLedgerEntry(double amount, String description) {
        this.ledger.addEntry(amount, description);
        this.presentNetCashflow = this.ledger.getNetCashflow();
        return true;
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

    // EFFECTS: returns the console string representation of the summary of this financial account.
    public String consoleRepr() {
        String repr = "\n";

        // header
        repr += "Financial Summary:\n";
        repr += "\n  Created: " + this.created;
        repr += "\n  Account-ID: " + this.id;
        repr += "\n  Owner: " + this.firstname + " " + this.lastname;

        if (this.ledger.getTotalEntries() < 1) {
            repr += "\n  Total Entries: " + this.ledger.getTotalEntries();
            return repr;
        }

        // body
        repr += "\n  Ledger:\n" + this.ledger.consoleRepr(2);
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

    // EFFECTS: returns the [writable] JSON object representation of this financial account.
    public JSONObject jsonRepr() {
        return (new JSONObject())
            .put("id", this.id)
            .put("created", this.created)
            .put("firstname", this.firstname)
            .put("lastname", this.lastname)
            .put("presentNetCashflow", this.presentNetCashflow)
            .put("targetNetCashflow", this.targetNetCashflow)
            .put("ledger", this.ledger.jsonRepr());
    }
}
