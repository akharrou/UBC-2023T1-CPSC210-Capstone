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

    // EFFECTS: returns [inflow|outflow] entry's string amount representation in dollars,
    //          with preceding negative sign if outflow and preceding positive sign if inflow.
    public String getAmountRepr() { return "+ $" + this.amount; }
}
