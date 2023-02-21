package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFinancialEntry {

    FinancialEntry fi0;
    FinancialEntry fi1;
    FinancialEntry fi2;

    FinancialEntry fo0;
    FinancialEntry fo1;
    FinancialEntry fo2;

    FinancialEntry fiFromJson;
    String fiJsonString = "{\n" +
            "    \"amount\": 12.23,\n" +
            "    \"created\": \"2023/02/20 17:26:41\",\n" +
            "    \"description\": \"sandwish\",\n" +
            "    \"id\": 1,\n" +
            "    \"type\": \"inflow\"\n" +
            "}";

    FinancialEntry foFromJson;
    String foJsonString = "{\n" +
            "    \"amount\": 82.23,\n" +
            "    \"created\": \"2023/02/20 18:07:29\",\n" +
            "    \"description\": \"bla\",\n" +
            "    \"id\": 4,\n" +
            "    \"type\": \"outflow\"\n" +
            "}";

    @BeforeEach
    public void setup() {
        fi0 = new Inflow(1, 0.00, "Inflow 0");
        fi1 = new Inflow(2, 0.01, "Inflow 1");
        fi2 = new Inflow(3, 23.72, "Inflow 2");
        fo0 = new Outflow(4, 0.00, "Outflow 0");
        fo1 = new Outflow(5, 0.01, "Outflow 1");
        fo2 = new Outflow(6, 32.27, "Outflow 2");

        fiFromJson = new Inflow(new JSONObject(fiJsonString));
        foFromJson = new Outflow(new JSONObject(foJsonString));
    }

    // (int id, double amount, String description) -> FinancialEntry
    @Test
    public void testFinancialEntryBasicConstructor() {

        // inflows
        assertEquals(1, fi0.getID());
        assertEquals(2, fi1.getID());
        assertEquals(3, fi2.getID());
        assertEquals("0.00", String.format("%,.2f", fi0.getAmount()));
        assertEquals("0.01", String.format("%,.2f", fi1.getAmount()));
        assertEquals("23.72", String.format("%,.2f", fi2.getAmount()));
        assertEquals("Inflow 0", fi0.getDescription());
        assertEquals("Inflow 1", fi1.getDescription());
        assertEquals("Inflow 2", fi2.getDescription());

        // outflows
        assertEquals(4, fo0.getID());
        assertEquals(5, fo1.getID());
        assertEquals(6, fo2.getID());
        assertEquals("0.00", String.format("%,.2f", fo0.getAmount()));
        assertEquals("0.01", String.format("%,.2f", fo1.getAmount()));
        assertEquals("32.27", String.format("%,.2f", fo2.getAmount()));
        assertEquals("Outflow 0", fo0.getDescription());
        assertEquals("Outflow 1", fo1.getDescription());
        assertEquals("Outflow 2", fo2.getDescription());

    }

    // (JSONObject entry) -> FinancialEntry
    @Test
    public void testFinancialEntryJsonConstructor() {

        // inflow
        assertEquals(1, fiFromJson.getID());
        assertEquals("12.23", String.format("%,.2f", fiFromJson.getAmount()));
        assertEquals("sandwish", fiFromJson.getDescription());
        assertEquals("2023/02/20 17:26:41", fiFromJson.getCreated());

        // outflow
        assertEquals(4, foFromJson.getID());
        assertEquals("82.23", String.format("%,.2f", foFromJson.getAmount()));
        assertEquals("bla", foFromJson.getDescription());
        assertEquals("2023/02/20 18:07:29", foFromJson.getCreated());

    }

    // (int ntabs) -> String
    @Test
    public void testConsoleRepr() {
        assertEquals(
                "        1      2023/02/20 17:26:41  sandwish             $12.23",
                fiFromJson.consoleRepr(2)
        );
    }

    // () -> JSONObject
    @Test
    public void testJsonRepr() {
        assertEquals(fiJsonString, fiFromJson.jsonRepr().toString(4));
    }
}
