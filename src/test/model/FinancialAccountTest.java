package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinancialAccountTest {

    FinancialAccount fa;
    FinancialAccount faFromJson;
    String faJsonString = "{\n" +
            "    \"ledger\": [\n" +
            "        {\n" +
            "            \"amount\": 12.23,\n" +
            "            \"created\": \"2023/02/20 17:26:41\",\n" +
            "            \"description\": \"sandwish\",\n" +
            "            \"id\": 1,\n" +
            "            \"type\": \"inflow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"amount\": 90.42,\n" +
            "            \"created\": \"2023/02/20 17:27:51\",\n" +
            "            \"description\": \"bought bike\",\n" +
            "            \"id\": 2,\n" +
            "            \"type\": \"inflow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"amount\": 5.99,\n" +
            "            \"created\": \"2023/02/20 17:28:06\",\n" +
            "            \"description\": \"bus fare\",\n" +
            "            \"id\": 3,\n" +
            "            \"type\": \"inflow\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"amount\": 82.23,\n" +
            "            \"created\": \"2023/02/20 18:07:29\",\n" +
            "            \"description\": \"bla\",\n" +
            "            \"id\": 4,\n" +
            "            \"type\": \"outflow\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"targetNetCashflow\": 10.23,\n" +
            "    \"firstname\": \"John\",\n" +
            "    \"created\": \"2023/02/20 17:25:27\",\n" +
            "    \"id\": \"42e042f4-90b1-4dbc-b11a-2f818a49adf1\",\n" +
            "    \"presentNetCashflow\": 26.409999999999997,\n" +
            "    \"lastname\": \"Doe\"\n" +
            "}";

    String faResetJsonString = "{\n" +
            "    \"ledger\": [],\n" +
            "    \"targetNetCashflow\": 10.23,\n" +
            "    \"firstname\": \"John\",\n" +
            "    \"created\": \"2023/02/20 17:25:27\",\n" +
            "    \"id\": \"42e042f4-90b1-4dbc-b11a-2f818a49adf1\",\n" +
            "    \"presentNetCashflow\": 0,\n" +
            "    \"lastname\": \"Doe\"\n" +
            "}";

    @BeforeEach
    public void setup() {
        fa = new FinancialAccount("John", "Doe");
        faFromJson = new FinancialAccount(new JSONObject(faJsonString));
    }

    @Test
    // (String first, String last) -> FinancialAccount
    public void testFinancialAccountBasicConstructor() {
        assertEquals("John", fa.getFirstname());
        assertEquals("Doe", fa.getLastname());
        assertEquals(0.0, fa.getPresentNetCashflow());
        assertEquals(0.0, fa.getTargetNetCashflow());
        assertEquals(new ArrayList<FinancialEntry>(), fa.getLedger());
    }

    @Test
    // (JSONObject account) -> FinancialAccount
    public void testFinancialAccountJsonConstructor() {
        assertEquals(UUID.fromString("42e042f4-90b1-4dbc-b11a-2f818a49adf1"), faFromJson.getID());
        assertEquals("John", faFromJson.getFirstname());
        assertEquals("Doe", faFromJson.getLastname());
        assertEquals("2023/02/20 17:25:27", faFromJson.getCreated());
        assertEquals("26.41", String.format("%,.2f", faFromJson.getPresentNetCashflow()));
        assertEquals("10.23", String.format("%,.2f", faFromJson.getTargetNetCashflow()));
    }

    @Test
    // (double amount, String description) -> void
    public void testRecordFinancialEntry() {

        fa.recordFinancialEntry(0.00, "entry 1");
        assertEquals(0.00, fa.getPresentNetCashflow());

        fa.recordFinancialEntry(30.00, "entry 2");
        assertEquals(30.00, fa.getPresentNetCashflow());

        fa.recordFinancialEntry(-25.00, "entry 3");
        assertEquals(5.00, fa.getPresentNetCashflow());

        fa.recordFinancialEntry(-105.00, "entry 4");
        assertEquals(-100.00, fa.getPresentNetCashflow());

        fa.recordFinancialEntry(1111.00, "entry 5");
        assertEquals(1011.00, fa.getPresentNetCashflow());

    }

    @Test
    // (double targetNetCashflow) -> void
    public void testSetTargetNetCashflow() {

        assertEquals(0.00, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(30.0);
        assertEquals(30.00, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(-25.0);
        assertEquals(-25, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(2.34);
        assertEquals(2.34, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(-1234.56);
        assertEquals(-1234.56, fa.getTargetNetCashflow());

    }

    @Test
    // () -> void
    public void testReset() {
        String expected = "Financial Summary:\n" +
                "\n" +
                "  Created: 2023/02/20 17:25:27\n" +
                "  Account-ID: 42e042f4-90b1-4dbc-b11a-2f818a49adf1\n" +
                "  Owner: John Doe\n" +
                "  Total Entries: 0";

        assertEquals("John", faFromJson.getFirstname());
        assertEquals("Doe", faFromJson.getLastname());
        assertEquals("2023/02/20 17:25:27", faFromJson.getCreated());
        assertEquals("26.41", String.format("%,.2f", faFromJson.getPresentNetCashflow()));
        assertEquals("10.23", String.format("%,.2f", faFromJson.getTargetNetCashflow()));

        faFromJson.reset();

        assertEquals("John", faFromJson.getFirstname());
        assertEquals("Doe", faFromJson.getLastname());
        assertEquals("2023/02/20 17:25:27", faFromJson.getCreated());
        assertEquals("0.00", String.format("%,.2f", faFromJson.getPresentNetCashflow()));
        assertEquals("10.23", String.format("%,.2f", faFromJson.getTargetNetCashflow()));

        assertEquals(expected, faFromJson.consoleRepr());
        assertEquals(faResetJsonString, faFromJson.jsonRepr().toString(4));
    }

    @Test
    // () -> String
    public void testConsoleRepr() {
        String expected = "Financial Summary:\n" +
                "\n" +
                "  Created: 2023/02/20 17:25:27\n" +
                "  Account-ID: 42e042f4-90b1-4dbc-b11a-2f818a49adf1\n" +
                "  Owner: John Doe\n" +
                "  Ledger:\n" +
                "\n" +
                "        Inflows:\n" +
                "            ID     Created              Description          Amount\n" +
                "            1      2023/02/20 17:26:41  sandwish             $12.23\n" +
                "            2      2023/02/20 17:27:51  bought bike          $90.42\n" +
                "            3      2023/02/20 17:28:06  bus fare             $5.99\n" +
                "\n" +
                "            Entries: ....................................... 3\n" +
                "            Median: ........................................ $12.23\n" +
                "            Average: ....................................... $36.21\n" +
                "            Total: ......................................... $108.64\n" +
                "\n" +
                "        Outflows:\n" +
                "            ID     Created              Description          Amount\n" +
                "            4      2023/02/20 18:07:29  bla                  $82.23\n" +
                "\n" +
                "            Entries: ....................................... 1\n" +
                "            Median: ........................................ $82.23\n" +
                "            Average: ....................................... $82.23\n" +
                "            Total: ......................................... $82.23\n" +
                "\n" +
                "  Total Entries: 4\n" +
                "  Present Net Cashflow: $26.41\n" +
                "  Target Net Cashflow: $10.23\n" +
                "  Financial Standing: above target 🟢";

        assertEquals(expected, faFromJson.consoleRepr());

        faFromJson.setTargetNetCashflow(36.41);
        assertEquals(
                expected.replace("10.23", "36.41")
                        .replace("above", "below")
                        .replace("🟢","🔴"),
                faFromJson.consoleRepr()
        );

        faFromJson.setTargetNetCashflow(26.409999999999997);
        assertEquals(
                expected.replace("10.23", "26.41")
                        .replace("above", "on")
                        .replace("🟢","🟠"),
                faFromJson.consoleRepr()
        );
    }

    @Test
    // () -> JSONObject
    public void testJsonRepr() {
        assertEquals(faJsonString, faFromJson.jsonRepr().toString(4));
    }

}
