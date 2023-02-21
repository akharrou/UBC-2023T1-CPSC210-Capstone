package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

import persistence.Writable;

// Represents a financial entry (inflow or outflow) that is to be stored in a financial ledger.
//   Inflows are by convention interpreted as being non-negative value amounts, and outflows as negative ones.
public abstract class FinancialEntry implements Writable {

    protected final int id;
    protected final double amount;
    protected final String description;
    protected final String created;

    // EFFECTS: constructs a new financial entry with an id, amount and description.
    //          datetime of creation is also stored as metadata.
    public FinancialEntry(int id, double amount, String description) {
        this.id = id;
        this.amount = Math.abs(amount);
        this.description = description;
        this.created = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    // EFFECTS: constructs a new financial entry from a JSONObject representation of said entry.
    public FinancialEntry(JSONObject entry) {
        this.id = entry.getInt("id");
        this.amount = entry.getDouble("amount");
        this.description = entry.getString("description");
        this.created = entry.getString("created");
    }

    // EFFECTS: returns entry's JSON representation.
    public JSONObject jsonRepr() {
        return (new JSONObject())
            .put("type", (this instanceof Inflow) ? "inflow" : "outflow")
            .put("id", this.id)
            .put("created", this.created)
            .put("amount", this.amount)
            .put("description", this.description);
    }

    // REQUIRES: non-negative ntabs
    // EFFECTS: returns entry's string console representation.
    public String consoleRepr(int ntabs) {
        String tabulation = String.format("%0" + ntabs + "d", 0).replace("0", "    ");
        return String.format("%s%-6s %-20s %-20s $%,.2f", tabulation,
            this.id, this.created, this.description, this.amount);
    }

    // EFFECTS: returns entry's identifier.
    public int getID() {
        return this.id;
    }

    // EFFECTS: returns [inflow|outflow] entry's amount.
    public double getAmount() {
        return this.amount;
    }

    // EFFECTS: returns entry's description
    public String getDescription() {
        return this.description;
    }

    // EFFECTS: returns entry's creation datetime.
    public String getCreated() {
        return this.created;
    }
}
