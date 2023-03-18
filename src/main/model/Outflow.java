package model;

import org.json.JSONObject;

// Represents a financial outflow (negative amount) entry.
public class Outflow extends FinancialEntry {

    // EFFECTS: constructs new inflow from given input data.
    public Outflow(Integer id, Double amount, String description) {
        super(id, amount, description);
    }

    // EFFECTS: constructs new inflow from given outflow JSON object representation.
    public Outflow(JSONObject entry) {
        super(entry);
    }

    // EFFECTS: returns [inflow|outflow] entry's string amount representation in dollars,
    //          with preceding negative sign if outflow and preceding positive sign if inflow.
    public String getAmountRepr() { return "– $" + this.amount; }
}
