package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;

// Represents a financial entry (inflow or outflow) that is to be stored in a financial ledger.
//   Inflows are by convention interpreted as non-negative values, and outflows as negative values.
public abstract class FinancialEntry
        implements Writable<JSONObject> {

    protected final int id;
    protected final double amount;
    protected final String description;
    protected final String created;

    // EFFECTS: creates a new financial entry that
    //          can be stored in a financial ledger
    public FinancialEntry(int id, double amount, String description) {
        this.id = id;
        this.amount = Math.abs(amount);
        this.description = description;
        this.created = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    // EFFECTS: creates a new financial entry that
    //          can be stored in a financial ledger
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

    // EFFECTS: returns entry's string representation.
    public String consoleRepr(int ntabs) {
        return String.format("%s%-6s %-20s %-20s $%,.2f", "    ".repeat(ntabs),
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
    public String getcreated() {
        return this.created;
    }
}
