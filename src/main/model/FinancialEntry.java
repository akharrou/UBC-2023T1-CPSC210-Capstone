package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class FinancialEntry implements Entry {

    protected final int id;
    protected final double amount;
    protected final String description;
    protected final String creationDatetime;

    // EFFECTS: creates a new financial entry that
    //          can be stored in a financial ledger
    public FinancialEntry(int id, double amount, String description) {
        this.id = id;
        this.amount = Math.abs(amount);
        this.description = description;
        this.creationDatetime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
    }

    // EFFECTS: returns entry's string representation.
    public String repr(int ntabs) {
        return String.format("%s%-6s %-20s %-20s $%,.2f", "    ".repeat(ntabs),
            this.id, this.creationDatetime, this.description, this.amount);
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
    public String getCreationDatetime() {
        return this.creationDatetime;
    }
}
