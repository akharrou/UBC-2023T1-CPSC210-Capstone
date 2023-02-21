package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InvalidInputExceptionTest {

    InvalidInputException exception;
    InvalidInputException exceptionWithString;

    @BeforeEach
    public void setup() {
        exception = new InvalidInputException();
        exceptionWithString = new InvalidInputException("abc");
    }

    @Test
    // () -> InvalidInputException
    public void TestInvalidInputExceptionBasicConstructor() {
        assertEquals("", exception.getInvalidInput());
        assertEquals(null, exception.getMessage());
    }

    @Test
    // (String invalidInput) -> InvalidInputException
    public void TestInvalidInputExceptionWithMessageConstructor() {
        assertEquals("abc", exceptionWithString.getInvalidInput());
        assertEquals("Invalid input: 'abc'. Try again.", exceptionWithString.getMessage());
    }

}
