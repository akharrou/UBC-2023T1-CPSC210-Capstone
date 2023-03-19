package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import persistence.Writable;

// Represents the financial account of some user. Holds profile information about the user,
//   and crucially a financial ledger of all of the user's financial inflows and outflows.
public class FinancialAccount implements Writable {

    private final UUID id;
    private final String created;
    private final String firstname;
    private final String lastname;
    private Double presentNetCashflow;
    private Double targetNetCashflow;
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

    // MODIFIES: this
    // EFFECTS: creates and adds a new financial entry to this accounts ledger and updates account information.
    //          if no exceptions are thrown, then the operation is considered successful.
    public void recordFinancialEntry(Double amount, String description) {
        this.ledger.addEntry(amount, description);
        this.presentNetCashflow = this.ledger.getNetCashflow();
    }

    // EFFECTS: returns account's financial ledger.
    public List<FinancialEntry> getLedger() {
        return this.ledger.getLedger();
    }

    // EFFECTS: returns account's identifier.
    public UUID getID() {
        return this.id;
    }

    // EFFECTS: returns account's creation datetime.
    public String getCreated() {
        return this.created;
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
    public Double getPresentNetCashflow() {
        return this.ledger.getNetCashflow();
    }

    // EFFECTS: returns account's target net cashflow.
    public Double getTargetNetCashflow() {
        return this.targetNetCashflow;
    }

    // MODIFIES: this
    // EFFECTS: sets account's target net cashflow.
    public void setTargetNetCashflow(Double targetNetCashflow) {
        this.targetNetCashflow = targetNetCashflow;
    }

    // MODIFIES: this
    // EFFECTS: resets this financial account (drops ledger entries a updates present net cashflow).
    public void reset() {
        this.ledger.drop();
        this.presentNetCashflow = 0.0;
    }

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
        } else if (this.presentNetCashflow.equals(this.targetNetCashflow)) {
            repr.append("\n  Financial Standing: on target 🟠");
        } else {
            repr.append("\n  Financial Standing: below target 🔴");
        }
        return repr.toString();
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
