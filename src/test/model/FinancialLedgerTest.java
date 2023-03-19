package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class FinancialLedgerTest {

    FinancialLedger fl;

    FinancialLedger flEmptyFromJson;
    String ledgerEmptyJsonString = "[]";

    FinancialLedger flFromJson;
    String ledgerJsonString = "[\n" +
            "    {\n" +
            "        \"amount\": 12.23,\n" +
            "        \"created\": \"2023/02/20 17:26:41\",\n" +
            "        \"description\": \"sandwish\",\n" +
            "        \"id\": 1,\n" +
            "        \"type\": \"inflow\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"amount\": 90.42,\n" +
            "        \"created\": \"2023/02/20 17:27:51\",\n" +
            "        \"description\": \"bought bike\",\n" +
            "        \"id\": 2,\n" +
            "        \"type\": \"inflow\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"amount\": 5.99,\n" +
            "        \"created\": \"2023/02/20 17:28:06\",\n" +
            "        \"description\": \"bus fare\",\n" +
            "        \"id\": 3,\n" +
            "        \"type\": \"inflow\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"amount\": 82.23,\n" +
            "        \"created\": \"2023/02/20 18:07:29\",\n" +
            "        \"description\": \"bla\",\n" +
            "        \"id\": 4,\n" +
            "        \"type\": \"outflow\"\n" +
            "    }\n" +
            "]";

    @BeforeEach
    public void setup() {
        fl = new FinancialLedger();
        flEmptyFromJson = new FinancialLedger(new JSONArray(ledgerEmptyJsonString));
        flFromJson = new FinancialLedger(new JSONArray(ledgerJsonString));
    }

    @Test
    public void testFinancialLedgerBasicConstructor() {
        assertEquals(0, fl.getTotalEntries());
        assertEquals(new ArrayList<FinancialEntry>(), fl.getLedger());
    }

    @Test
    public void testFinancialLedgerJsonConstructor() {
        assertEquals(4, flFromJson.getTotalEntries()); // int
        assertEquals(3, flFromJson.getTotalInflowEntries()); // long
        assertEquals("108.64", String.format("%,.2f", flFromJson.getInflowSum())); // double
        assertEquals("36.21", String.format("%,.2f", flFromJson.getAverageInflow()));
        assertEquals("12.23", String.format("%,.2f", flFromJson.getMedianInflow()));
        assertEquals(1, flFromJson.getTotalOutflowEntries()); // long
        assertEquals("82.23", String.format("%,.2f", flFromJson.getOutflowSum())); // double
        assertEquals("82.23", String.format("%,.2f", flFromJson.getAverageOutflow())); // double
        assertEquals("82.23", String.format("%,.2f", flFromJson.getMedianOutflow())); // double
        assertEquals("26.41", String.format("%,.2f", flFromJson.getNetCashflow())); // double
    }

    @Test
    public void testAddEntry() {
        assertEquals(0, fl.getTotalEntries());

        fl.addEntry(0, "entry 1");
        assertEquals(1, fl.getTotalEntries());
        assertEquals(1, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());
        assertEquals(0, fl.getNetCashflow());

        fl.addEntry(1, "entry 2");
        assertEquals(2, fl.getTotalEntries());
        assertEquals(2, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());
        assertEquals(1, fl.getNetCashflow());

        fl.addEntry(2, "entry 3");
        assertEquals(3, fl.getTotalEntries());
        assertEquals(3, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());
        assertEquals(3, fl.getNetCashflow());

        fl.addEntry(-1, "entry 4");
        assertEquals(4, fl.getTotalEntries());
        assertEquals(3, fl.getTotalInflowEntries());
        assertEquals(1, fl.getTotalOutflowEntries());
        assertEquals(2, fl.getNetCashflow());

        fl.addEntry(-2, "entry 5");
        assertEquals(5, fl.getTotalEntries());
        assertEquals(3, fl.getTotalInflowEntries());
        assertEquals(2, fl.getTotalOutflowEntries());
        assertEquals(0, fl.getNetCashflow());
    }

    @Test
    public void testDropLedger() {
        assertEquals(0, fl.getTotalEntries());
        assertEquals(0, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());
        assertEquals(0, fl.getNetCashflow());

        fl.addEntry(100, "entry 1");
        fl.addEntry(50, "entry 2");
        fl.addEntry(-10, "entry 3");
        fl.addEntry(-30, "entry 4");
        assertEquals(4, fl.getTotalEntries());
        assertEquals(2, fl.getTotalInflowEntries());
        assertEquals(2, fl.getTotalOutflowEntries());
        assertEquals(110, fl.getNetCashflow());

        fl.drop();
        assertEquals(0, fl.getTotalEntries());
        assertEquals(0, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());
        assertEquals(0, fl.getNetCashflow());
    }

    @Test
    public void testGetAverageInflow() {
        // (1+2+3)/3 = 2
        fl.addEntry(1, "entry 1");
        fl.addEntry(2, "entry 2");
        fl.addEntry(3, "entry 3");
        assertEquals(2, fl.getAverageInflow());
    }

    @Test
    public void testGetMedianInflow() {

        // 1,3,10 -> 3
        // 1,2,3,4,8,10 -> 3.5
        // 1,2,3,4,8,10,11 -> 4

        fl.addEntry(1, "entry 1");
        fl.addEntry(3, "entry 2");
        fl.addEntry(10, "entry 3");
        assertEquals(3, fl.getMedianInflow());

        fl.addEntry(8, "entry 4");
        fl.addEntry(2, "entry 5");
        fl.addEntry(4, "entry 6");
        assertEquals(3.5, fl.getMedianInflow());

        fl.addEntry(11, "entry 7");
        assertEquals(4, fl.getMedianInflow());
    }

    @Test
    public void testGetAverageOutflow() {
        // (-1+-2+-3)/3 = -2
        fl.addEntry(-1, "outflow entry 1");
        fl.addEntry(-2, "outflow entry 2");
        fl.addEntry(-3, "outflow entry 3");
        assertEquals(2, fl.getAverageOutflow());
    }

    @Test
    public void testGetMedianOutflow() {

        // -10,-3,-1 -> -3
        // -10,-8,-4,-3,-2,-1 -> -3.5
        // -11,-10,-8,-4,-3,-2,-1 -> -4

        fl.addEntry(-1, "outflow entry 1");
        fl.addEntry(-3, "outflow entry 2");
        fl.addEntry(-10, "outflow entry 3");
        assertEquals(3, fl.getMedianOutflow());

        fl.addEntry(-8, "outflow entry 4");
        fl.addEntry(-2, "outflow entry 5");
        fl.addEntry(-4, "outflow entry 6");
        assertEquals(3.5, fl.getMedianOutflow());

        fl.addEntry(-11, "outflow entry 7");
        assertEquals(4, fl.getMedianOutflow());
    }

    @Test
    public void testGetPresentNetCashflow() {
        assertEquals(0, fl.getNetCashflow());

        fl.addEntry(1, "entry 1");
        fl.addEntry(3, "entry 2");
        fl.addEntry(10, "entry 3");
        fl.addEntry(8, "entry 4");
        fl.addEntry(2, "entry 5");
        fl.addEntry(4, "entry 6");
        fl.addEntry(11, "entry 7");
        assertEquals(39, fl.getNetCashflow());

        fl.addEntry(-1, "outflow entry 1");
        fl.addEntry(-3, "outflow entry 2");
        fl.addEntry(-10, "outflow entry 3");
        assertEquals(25, fl.getNetCashflow());

        fl.addEntry(-8, "outflow entry 4");
        fl.addEntry(-2, "outflow entry 5");
        fl.addEntry(-4, "outflow entry 6");
        assertEquals(11, fl.getNetCashflow());

        fl.addEntry(-11, "outflow entry 7");
        assertEquals(0, fl.getNetCashflow());
    }

    @Test
    public void testJsonRepr() {
        assertEquals(ledgerEmptyJsonString, flEmptyFromJson.jsonRepr().toString(4));
        assertEquals(ledgerJsonString, flFromJson.jsonRepr().toString(4));
    }

    @Test
    public void testConsoleRepr() {

        String expected1 = "\n";
        assertEquals(expected1, flEmptyFromJson.consoleRepr(2));

        String expected2 = "\n" +
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
                "            Total: ......................................... $82.23\n";
        assertEquals(expected2, flFromJson.consoleRepr(2));

    }
}
