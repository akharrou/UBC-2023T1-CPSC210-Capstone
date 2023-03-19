package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents a financial ledger having a collection of financial (inflow or outflow) entries.
public class FinancialLedger {

    private List<FinancialEntry> ledger;

    // EFFECTS: constructs an empty financial ledger.
    public FinancialLedger() {
        this.ledger = new ArrayList<FinancialEntry>();
    }

    // EFFECTS: constructs the financial ledger represented by the given JSON array.
    // CITATIONS:
    //  [1]: https://stackoverflow.com/a/54260629/13992057
    //  [2]: https://stackoverflow.com/a/21920224/13992057 + IDE popup docs
    public FinancialLedger(JSONArray ledger) {
        this.ledger = (List<FinancialEntry>)
            IntStream.range(0, ledger.length())
                .mapToObj((i) -> {
                    JSONObject e = ledger.getJSONObject(i);
                    return e.getString("type").equals("inflow")
                        ? new Inflow(e) : new Outflow(e); })
                .collect(Collectors.toList());
    }

    // EFFECTS: returns ledger.
    public List<FinancialEntry> getLedger() {
        return this.ledger;
    }

    // MODIFIES: this
    // EFFECTS: adds a financial entry to this financial ledger list with an amount and description.
    //          if amount >= 0 → the entry is recorded as an inflow
    //          else [if amount < 0] → the entry is recorded as an outflow
    public void addEntry(double amount, String description) {
        if (amount >= 0) {
            this.ledger.add(new Inflow(this.ledger.size() + 1, amount, description));
        } else { // if (amount < 0)
            this.ledger.add(new Outflow(this.ledger.size() + 1, amount, description));
        }
    }

    // MODIFIES: this
    // EFFECTS: removes all entries from this ledger list.
    public void drop() {
        this.ledger.clear();
    }

    // EFFECTS: returns the number of entries in this ledger list.
    public int getTotalEntries() {
        return this.ledger.size();
    }

    // EFFECTS: returns the number of inflow entries in this ledger list.
    public long getTotalInflowEntries() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Inflow)
            .count();
    }

    // EFFECTS: returns the sum of inflows in this ledger list.
    public double getInflowSum() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Inflow)
            .mapToDouble(e -> e.getAmount())
            .sum();
    }

    // EFFECTS: returns the average inflow amount in this ledger list.
    public double getAverageInflow() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Inflow)
            .mapToDouble(e -> e.getAmount())
            .average()
            .getAsDouble();
    }

    // EFFECTS: returns the median inflow amount in this ledger list.
    public double getMedianInflow() {
        List<FinancialEntry> inflows = this.ledger.stream()
                .filter(entry -> entry instanceof Inflow)
                .sorted((e1, e2) -> (int) (e1.getAmount() - e2.getAmount()))
                .collect(Collectors.toList());
        if (inflows.size() % 2 == 0) {
            double middleAmount1 = inflows.get(inflows.size() / 2).getAmount();
            double middleAmount2 = inflows.get((inflows.size() / 2) - 1).getAmount();
            return (middleAmount1 + middleAmount2) / 2;
        } else {
            return inflows.get(inflows.size() / 2).getAmount();
        }
    }

    // EFFECTS: returns a count of the number of outflow entries in the ledger.
    public long getTotalOutflowEntries() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Outflow)
            .count();
    }

    // EFFECTS: returns sum of outflows
    public double getOutflowSum() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Outflow)
            .mapToDouble(FinancialEntry::getAmount)
            .sum();
    }

    // EFFECTS: returns ledger's average outflow entry.
    public double getAverageOutflow() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Outflow)
            .mapToDouble(FinancialEntry::getAmount)
            .average()
            .getAsDouble();
    }

    // EFFECTS: returns ledger's median outflow entry.
    public double getMedianOutflow() {
        List<FinancialEntry> outflows = this.ledger.stream()
                .filter(entry -> entry instanceof Outflow)
                .sorted((e1, e2) -> (int) (e1.getAmount() - e2.getAmount()))
                .collect(Collectors.toList());
        if (outflows.size() % 2 == 0) {
            double middleAmount1 = outflows.get(outflows.size() / 2).getAmount();
            double middleAmount2 = outflows.get((outflows.size() / 2) - 1).getAmount();
            return (middleAmount1 + middleAmount2) / 2;
        } else {
            return outflows.get(outflows.size() / 2).getAmount();
        }
    }

    // EFFECTS: returns ledger's net cashflow
    public double getNetCashflow() {
        return this.getInflowSum() - this.getOutflowSum();
    }

    // EFFECTS: get a string representation of the entire financial ledger
    public String consoleRepr(int ntabs) {
        return this.consoleReprInflows(ntabs) + this.consoleReprOutflows(ntabs);
    }

    // REQUIRES: non-negative ntabs
    // EFFECTS: get a string representation of all inflows in ledger
    // CITATIONS:
    //  [1]: https://stackoverflow.com/a/6857936/13992057
    public String consoleReprInflows(int ntabs) {
        String tabulation = String.format("%0" + ntabs + "d", 0).replace("0", "    ");
        String tabulationPlus = String.format("%0" + (ntabs + 1) + "d", 0).replace("0", "    ");
        String tabulation39 = String.format("%0" + 39 + "d", 0).replace("0", ".");
        String tabulation40 = String.format("%0" + 40 + "d", 0).replace("0", ".");
        String tabulation41 = String.format("%0" + 41 + "d", 0).replace("0", ".");
        return (this.getTotalInflowEntries() < 1) ? "\n" :
            String.format("\n%s%s\n", tabulation, "Inflows:")
            + this.ledger.stream()
                .filter(entry -> entry instanceof Inflow)
                .map(entry -> entry.consoleRepr(ntabs + 1))
                .reduce(String.format("%s%-6s %-20s %-20s %s", tabulationPlus,
                    "ID", "Created", "Description", "Amount"), (accum, entryRepr) -> (accum + "\n" + entryRepr))
            + "\n\n"
            + String.format("%s%39s %d\n", tabulationPlus, "Entries: " + tabulation39, this.getTotalInflowEntries())
            + String.format("%s%40s $%,.2f\n", tabulationPlus, "Median: " + tabulation40, this.getMedianInflow())
            + String.format("%s%39s $%,.2f\n", tabulationPlus, "Average: " + tabulation39, this.getAverageInflow())
            + String.format("%s%41s $%,.2f\n", tabulationPlus, "Total: " + tabulation41, this.getInflowSum());
    }

    // REQUIRES: non-negative ntabs
    // EFFECTS: get a string representation of all outflows in ledger
    public String consoleReprOutflows(int ntabs) {
        String tabulation = String.format("%0" + ntabs + "d", 0).replace("0", "    ");
        String tabulationPlus = String.format("%0" + (ntabs + 1) + "d", 0).replace("0", "    ");
        String tabulation39 = String.format("%0" + 39 + "d", 0).replace("0", ".");
        String tabulation40 = String.format("%0" + 40 + "d", 0).replace("0", ".");
        String tabulation41 = String.format("%0" + 41 + "d", 0).replace("0", ".");
        return (this.getTotalOutflowEntries() < 1) ? "" :
            String.format("\n%s%s\n", tabulation, "Outflows:")
            + this.ledger.stream()
                .filter(entry -> entry instanceof Outflow)
                .map(entry -> entry.consoleRepr(ntabs + 1))
                .reduce(String.format("%s%-6s %-20s %-20s %s", tabulationPlus,
                    "ID", "Created", "Description", "Amount"), (accum, entryRepr) -> (accum + "\n" + entryRepr))
            + "\n\n"
            + String.format("%s%39s %d\n", tabulationPlus, "Entries: " + tabulation39, this.getTotalOutflowEntries())
            + String.format("%s%40s $%,.2f\n", tabulationPlus, "Median: " + tabulation40, this.getMedianOutflow())
            + String.format("%s%39s $%,.2f\n", tabulationPlus, "Average: " + tabulation39, this.getAverageOutflow())
            + String.format("%s%41s $%,.2f\n", tabulationPlus, "Total: " + tabulation41, this.getOutflowSum());
    }

    // EFFECTS: returns the [writable] JSON array representation of this financial ledger
    //          with all its financial entries.
    public JSONArray jsonRepr() {
        JSONArray jsonArr = new JSONArray();
        this.ledger.stream().forEachOrdered(e -> jsonArr.put(e.jsonRepr()));
        return jsonArr;
    }

}
