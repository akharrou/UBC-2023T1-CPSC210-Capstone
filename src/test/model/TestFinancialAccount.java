package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFinancialAccount {

    FinancialAccount fa;

    @BeforeEach
    public void setup() {
        fa = new FinancialAccount("John", "Doe");
    }

    @Test
    public void testFinancialAccount() {
        assertEquals("John", fa.getFirstname());
        assertEquals("Doe", fa.getLastname());
        assertEquals(0.0, fa.getPresentNetCashflow());
        assertEquals(0.0, fa.getTargetNetCashflow());
    }

    @Test
    public void testRecordLedgerEntry() {

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
    public void testSetTargetNetCashflow() {
        assertEquals(0.00, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(30);
        assertEquals(30.00, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(-25);
        assertEquals(-25, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(2.34);
        assertEquals(2.34, fa.getTargetNetCashflow());

        fa.setTargetNetCashflow(-1234.56);
        assertEquals(-1234.56, fa.getTargetNetCashflow());
    }

    @Test
    public void testConsoleRepr() {
        assertNotNull(fa.consoleRepr());
        assertNotEquals("", fa.consoleRepr());

        // PCF = $30.00
        fa.recordFinancialEntry(0.00, "entry 1");
        fa.recordFinancialEntry(30.00, "entry 2");
        assertNotNull(fa.consoleRepr());
        assertNotEquals("", fa.consoleRepr());

        // PCF = $1011.00
        fa.recordFinancialEntry(-25.00, "entry 3");
        fa.recordFinancialEntry(-105.00, "entry 4");
        fa.recordFinancialEntry(1111.00, "entry 5");
        assertNotNull(fa.consoleRepr());
        assertNotEquals("", fa.consoleRepr());
    }

    @Test
    public void testJsonRepr() {
        assertNotNull(fa.jsonRepr());
        assertNotEquals("", fa.jsonRepr());

        // PCF = $30.00
        fa.recordFinancialEntry(0.00, "entry 1");
        fa.recordFinancialEntry(30.00, "entry 2");
        fa.setTargetNetCashflow(30);
        assertNotNull(fa.jsonRepr());
        assertNotEquals("", fa.jsonRepr());

        // PCF = $1011.00
        fa.recordFinancialEntry(-25.00, "entry 3");
        fa.recordFinancialEntry(-105.00, "entry 4");
        fa.recordFinancialEntry(1111.00, "entry 5");
        fa.setTargetNetCashflow(-23.42);
        assertNotNull(fa.jsonRepr());
        assertNotEquals("", fa.jsonRepr());
        System.out.println(fa.jsonRepr());
    }
}
