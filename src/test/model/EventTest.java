package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the Event class
 */
public class EventTest {
    private Event e;
    private Date d;

    //NOTE: there's a 1 second delay tolerance between event datetimes.

    @BeforeEach
    public void runBefore() {
        e = new Event("new account created");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("new account created", e.getDescription());
        assertTrue(Math.abs(d.getTime() - e.getDate().getTime()) <= 1000);
    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "new account created", e.toString());
    }
}
