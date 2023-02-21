package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.FinancialAccount;

public class WriterTest {
    // NOTE: the strategy is to write data to a file and then use the reader to read
    //       it back in and check that we read in a copy of what was written out.

    String emptyAccountJsonString = "{\n" +
            "    \"ledger\": [],\n" +
            "    \"targetNetCashflow\": 10.23,\n" +
            "    \"firstname\": \"John\",\n" +
            "    \"created\": \"2023/02/20 17:25:27\",\n" +
            "    \"id\": \"42e042f4-90b1-4dbc-b11a-2f818a49adf1\",\n" +
            "    \"presentNetCashflow\": 0,\n" +
            "    \"lastname\": \"Doe\"\n" +
            "}";

    String nonEmptyAccountJsonString = "{\n" +
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

    @Test
    void testWriterInvalidFile() {
        try {
            FinancialAccount fa = new FinancialAccount("John", "Doe");
            new Writer().write(fa, "./data/my\0illegal:fileName.json");
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAccount() {
        Writer writer = new Writer();
        Reader reader = new Reader();
        try {

            writer.write(new FinancialAccount(new JSONObject(emptyAccountJsonString)),
                "./data/test/WriterEmptyAccount.json");

            FinancialAccount fa = new FinancialAccount(new JSONObject(reader.read(
                "./data/test/WriterEmptyAccount.json")));

            assertEquals("John", fa.getFirstname());
            assertEquals("Doe", fa.getLastname());
            assertEquals("2023/02/20 17:25:27", fa.getCreated());
            assertEquals("0.00", String.format("%,.2f", fa.getPresentNetCashflow()));
            assertEquals("10.23", String.format("%,.2f", fa.getTargetNetCashflow()));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNonEmptyAccount() {
        Writer writer = new Writer();
        Reader reader = new Reader();
        try {

            writer.write(new FinancialAccount(new JSONObject(nonEmptyAccountJsonString)),
                "./data/test/WriterNonEmptyAccount.json");

            FinancialAccount fa = new FinancialAccount(new JSONObject(reader.read(
                "./data/test/WriterNonEmptyAccount.json")));

            assertEquals(UUID.fromString("42e042f4-90b1-4dbc-b11a-2f818a49adf1"), fa.getID());
            assertEquals("John", fa.getFirstname());
            assertEquals("Doe", fa.getLastname());
            assertEquals("2023/02/20 17:25:27", fa.getCreated());
            assertEquals("26.41", String.format("%,.2f", fa.getPresentNetCashflow()));
            assertEquals("10.23", String.format("%,.2f", fa.getTargetNetCashflow()));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
