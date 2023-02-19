package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFinancialLedger {

    FinancialLedger fl;

    @BeforeEach
    public void setup() {
        fl = new FinancialLedger();
    }

    @Test
    public void testFinancialLedger() {
        assertEquals(0, fl.getTotalEntries());
    }

    @Test
    public void testAddEntry() {
        assertEquals(0, fl.getTotalEntries());

        fl.addEntry(0, "entry 1");
        assertEquals(1, fl.getTotalEntries());
        assertEquals(1, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());

        fl.addEntry(1, "entry 2");
        assertEquals(2, fl.getTotalEntries());
        assertEquals(2, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());

        fl.addEntry(2, "entry 3");
        assertEquals(3, fl.getTotalEntries());
        assertEquals(3, fl.getTotalInflowEntries());
        assertEquals(0, fl.getTotalOutflowEntries());

        fl.addEntry(-1, "entry 4");
        assertEquals(4, fl.getTotalEntries());
        assertEquals(3, fl.getTotalInflowEntries());
        assertEquals(1, fl.getTotalOutflowEntries());

        fl.addEntry(-2, "entry 5");
        assertEquals(5, fl.getTotalEntries());
        assertEquals(3, fl.getTotalInflowEntries());
        assertEquals(2, fl.getTotalOutflowEntries());
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
}
