package model;

import org.json.JSONObject;

public class Inflow extends FinancialEntry {

    // EFFECTS: constructs new inflow from given input.
    public Inflow(int id, double amount, String description) {
        super(id, amount, description);
    }

    // EFFECTS: constructs new inflow from given inflow JSON object representation.
    public Inflow(JSONObject entry) {
        super(entry);
    }

}
