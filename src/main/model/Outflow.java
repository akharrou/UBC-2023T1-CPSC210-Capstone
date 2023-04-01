package model;

import org.json.JSONObject;

// Represents a financial outflow (negative amount) entry.
public class Outflow extends FinancialEntry {

    // EFFECTS: constructs new inflow from given input data.
    public Outflow(Integer id, Double amount, String description) {
        super(id, amount, description);
        EventLog.getInstance().logEvent(new Event(
                "[FinancialEntry] new outflow financial entry constructed"
        ));
    }

    // EFFECTS: constructs new inflow from given outflow JSON object representation.
    public Outflow(JSONObject entry) {
        super(entry);
        EventLog.getInstance().logEvent(new Event(
                "[FinancialEntry] new outflow financial entry constructed (from JSON object)"
        ));
    }

    // EFFECTS: returns outflow entry's string amount representation in dollars, with a negative sign prefix.
    public String getAmountRepr() {
        return String.format("– $%,.2f", this.amount);
    }
}
