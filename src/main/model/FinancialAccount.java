package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.json.JSONObject;

import persistence.Writable;

// Represents the financial account of some user. Holds profile information about the user,
//   and crucially a financial ledger of all of the user's financial inflows and outflows.
// !TODO: double check all is tested
public class FinancialAccount implements Writable {

    private final UUID id;
    private final String created;
    private final String firstname;
    private final String lastname;
    private double presentNetCashflow;
    private double targetNetCashflow;
    private FinancialLedger ledger;

    // EFFECTS: constructs a new financial account populated with a first and last name.
    public FinancialAccount(String first, String last) {
        this.id = UUID.randomUUID();
        this.created = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        this.firstname = first;
        this.lastname = last;
        this.presentNetCashflow = 0.0;
        this.targetNetCashflow = 0.0;
        this.ledger = new FinancialLedger();
    }

    // REQUIRES: non-null JSONObject
    // EFFECTS: constructs the financial account represented by the given JSON object.
    public FinancialAccount(JSONObject account) {
        this.id = UUID.fromString(account.getString("id"));
        this.created = account.getString("created");
        this.firstname = account.getString("firstname");
        this.lastname = account.getString("lastname");
        this.presentNetCashflow = account.getDouble("presentNetCashflow");
        this.targetNetCashflow = account.getDouble("targetNetCashflow");
        this.ledger = new FinancialLedger(account.getJSONArray("ledger"));
    }

    // REQUIRES: non-null financial ledger field
    // MODIFIES: this
    // EFFECTS: creates and adds a new financial entry to this accounts ledger and updates account information.
    //          if no exceptions are thrown, then the operation is considered successful.
    public void recordFinancialEntry(double amount, String description) {
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

    // REQUIRES: non-null financial ledger field
    // MODIFIES: this
    // EFFECTS: resets this financial account (drops ledger entries a updates present net cashflow).
    public void reset() {
        this.ledger.drop();
        this.presentNetCashflow = 0.0;
    }

    // REQUIRES: non-null financial ledger field
    // EFFECTS: returns the console string representation of the summary report of this financial account.
    public String consoleRepr() {
        StringBuilder repr = new StringBuilder();

        // header
        repr.append("Financial Summary:\n");
        repr.append("\n  Created: " + this.created);
        repr.append("\n  Account-ID: " + this.id);
        repr.append("\n  Owner: " + this.firstname + " " + this.lastname);

        if (this.ledger.getTotalEntries() < 1) {
            repr.append("\n  Total Entries: " + this.ledger.getTotalEntries());
            return repr.toString();
        }

        // body
        repr.append("\n  Ledger:\n" + this.ledger.consoleRepr(2));
        // footer
        repr.append(String.format("\n  Total Entries: %d", this.ledger.getTotalEntries()));
        repr.append(String.format("\n  Present Net Cashflow: $%,.2f", this.ledger.getNetCashflow()));
        repr.append(String.format("\n  Target Net Cashflow: $%,.2f", this.targetNetCashflow));

        if (this.presentNetCashflow > this.targetNetCashflow) {
            repr.append("\n  Financial Standing: above target 🟢");
        } else if (this.presentNetCashflow == this.targetNetCashflow) {
            repr.append("\n  Financial Standing: on target 🟠");
        } else {
            repr.append("\n  Financial Standing: below target 🔴");
        }
        return repr.toString();
    }

    // REQUIRES: non-null financial ledger field
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
