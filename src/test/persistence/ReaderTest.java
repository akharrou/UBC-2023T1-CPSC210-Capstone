package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.FinancialAccount;
import model.FinancialLedger;

public class ReaderTest {

    @Test
    void testReaderNonExistentFile() {
        try {
            new Reader().read("./data/test/ReaderNoSuchFile.json");
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAccount() {
        FinancialAccount fa;
        FinancialLedger fl;
        Reader reader = new Reader();
        try {

            fa = new FinancialAccount(new JSONObject(reader.read("./data/test/ReaderEmptyAccount.json")));
            assertEquals(UUID.fromString("42e042f4-90b1-4dbc-b11a-2f818a49adf1"), fa.getID());
            assertEquals("0.00", String.format("%,.2f", fa.getPresentNetCashflow()));

            fl = new FinancialLedger(new JSONArray(reader.read("./data/test/ReaderEmptyLedger.json")));
            assertEquals(0, fl.getTotalEntries());
            assertEquals(0, fl.getTotalInflowEntries());
            assertEquals(0, fl.getTotalOutflowEntries());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNonEmptyAccount() {
        FinancialAccount fa;
        FinancialLedger fl;
        Reader reader = new Reader();
        try {

            fa = new FinancialAccount(new JSONObject(
                reader.read("./data/test/ReaderNonEmptyAccount.json")));
            assertEquals(UUID.fromString("42e042f4-90b1-4dbc-b11a-2f818a49adf1"), fa.getID());
            assertEquals("26.41", String.format("%,.2f", fa.getPresentNetCashflow()));

            fl = new FinancialLedger(new JSONArray(reader.read("./data/test/ReaderNonEmptyLedger.json")));
            assertEquals(4, fl.getTotalEntries());
            assertEquals(3, fl.getTotalInflowEntries());
            assertEquals(1, fl.getTotalOutflowEntries());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
