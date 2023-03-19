package model;

import org.json.JSONObject;

// Represents a financial inflow (non-negative amount) entry.
public class Inflow extends FinancialEntry {

    // EFFECTS: constructs new inflow from given input.
    public Inflow(Integer id, Double amount, String description) {
        super(id, amount, description);
    }

    // EFFECTS: constructs new inflow from given inflow JSON object representation.
    public Inflow(JSONObject entry) {
        super(entry);
    }

    // EFFECTS: returns inflow entry's string amount representation in dollars, with a positive sign prefix.
    public String getAmountRepr() {
        return String.format("+ $%,.2f", this.amount);
    }
}
