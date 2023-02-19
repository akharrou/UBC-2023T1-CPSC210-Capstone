package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FinancialLedger {

    private List<FinancialEntry> ledger;

    public FinancialLedger() {
        this.ledger = new ArrayList<FinancialEntry>();
    }

    // EFFECTS: adds a financial inflow or outflow to account's
    //          financial ledger.
    public void addEntry(double amount, String description) {
        if (amount >= 0) {
            this.ledger.add(new Inflow(this.ledger.size() + 1, amount, description));
        } else if (amount < 0) {
            this.ledger.add(new Outflow(this.ledger.size() + 1, amount, description));
        }
    }

    public int getTotalEntries() {
        return this.ledger.size();
    }

    // EFFECTS: return a count of the number of inflow entries in the ledger.
    public long getTotalInflowEntries() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Inflow)
            .count();
    }

    // EFFECTS: returns sum of inflows
    public double getInflowSum() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Inflow)
            .mapToDouble(e -> e.getAmount())
            .sum();
    }

    // EFFECTS: returns ledger's average inflow.
    public double getAverageInflow() {
        return this.ledger.stream()
            .filter(entry -> entry instanceof Inflow)
            .mapToDouble(e -> e.getAmount())
            .average()
            .getAsDouble();
    }

    // EFFECTS: returns ledger's median inflow entry.
    public double getMedianInflow() {
        List<FinancialEntry> inflows = this.ledger.stream()
                .filter(entry -> entry instanceof Inflow)
                .sorted((e1, e2) -> (int) (e1.getAmount() - e2.getAmount()))
                .collect(Collectors.toList());
        if (inflows.size() % 2 == 0) {
            // the average of the two middle values; take i and i-1 (because indices and java always rounds down)
            return (inflows.get(inflows.size() / 2).getAmount()
                  + inflows.get((inflows.size() / 2) - 1).getAmount())
                  / 2;
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
            return (outflows.get(outflows.size() / 2).getAmount()
                  + outflows.get((outflows.size() / 2) - 1).getAmount())
                  / 2;
        } else {
            return outflows.get(outflows.size() / 2).getAmount();
        }
    }

    // EFFECTS: returns ledger's net cashflow
    public double getNetCashflow() {
        return this.getInflowSum() - this.getOutflowSum();
    }

    // EFFECTS: get a string representation of the entire financial ledger
    public String repr(int ntabs) {
        return this.reprInflows(ntabs) + this.reprOutflows(ntabs);
    }

    // EFFECTS: get a string representation of all inflows in ledger
    public String reprInflows(int ntabs) {
        return (this.getTotalInflowEntries() < 1) ? "\n" :
            String.format("\n%s%s\n", "    ".repeat(ntabs), "Inflows:")
            + this.ledger.stream()
                .filter(entry -> entry instanceof Inflow)
                .map(entry -> entry.repr(ntabs + 1))
                .reduce(String.format("%s%-6s %-20s %-20s %s", "    ".repeat(ntabs + 1),
                    "ID", "Created", "Description", "Amount"), (accum, entryRepr) -> (accum + "\n"
                    + entryRepr))
            + "\n\n"
            + String.format("%s%39s %d\n", "    ".repeat(ntabs + 1), "Entries: " + ".".repeat(39),
                this.getTotalInflowEntries())
            + String.format("%s%40s $%,.2f\n", "    ".repeat(ntabs + 1), "Median: " + ".".repeat(40),
                this.getMedianInflow())
            + String.format("%s%39s $%,.2f\n", "    ".repeat(ntabs + 1), "Average: " + ".".repeat(39),
                this.getAverageInflow())
            + String.format("%s%41s $%,.2f\n", "    ".repeat(ntabs + 1), "Total: " + ".".repeat(41),
                this.getInflowSum());
    }

    // EFFECTS: get a string representation of all outflows in ledger
    public String reprOutflows(int ntabs) {
        return (this.getTotalOutflowEntries() < 1) ? "" :
            String.format("\n%s%s\n", "    ".repeat(ntabs), "Outflows:")
            + this.ledger.stream()
                .filter(entry -> entry instanceof Outflow)
                .map(entry -> entry.repr(ntabs + 1))
                .reduce(String.format("%s%-6s %-20s %-20s %s", "    ".repeat(ntabs + 1),
                    "ID", "Created", "Description", "Amount"), (accum, entryRepr) -> (accum + "\n"
                    + entryRepr))
            + "\n\n"
            + String.format("%s%39s %d\n", "    ".repeat(ntabs + 1), "Entries: " + ".".repeat(39),
                this.getTotalOutflowEntries())
            + String.format("%s%40s $%,.2f\n", "    ".repeat(ntabs + 1), "Median: " + ".".repeat(40),
                this.getMedianOutflow())
            + String.format("%s%39s $%,.2f\n", "    ".repeat(ntabs + 1), "Average: " + ".".repeat(39),
                this.getAverageOutflow())
            + String.format("%s%41s $%,.2f\n", "    ".repeat(ntabs + 1), "Total: " + ".".repeat(41),
                this.getOutflowSum());
    }

}
