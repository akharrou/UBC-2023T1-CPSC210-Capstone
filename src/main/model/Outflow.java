package model;

import org.json.JSONObject;

public class Outflow extends FinancialEntry {

    // EFFECTS: constructs new inflow from given input data.
    public Outflow(int id, double amount, String description) {
        super(id, amount, description);
    }

    // EFFECTS: constructs new inflow from given outflow JSON object representation.
    public Outflow(JSONObject entry) {
        super(entry);
    }

}
