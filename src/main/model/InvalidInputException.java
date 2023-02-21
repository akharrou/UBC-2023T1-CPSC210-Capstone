package model;

// Represents an instance of an invalid input event.
public class InvalidInputException extends Exception {

    private String invalidInput;

    // EFFECTS: constructs an invalid input exception.
    public InvalidInputException() {
        super();
        this.invalidInput = "";
    }

    // EFFECTS: constructs an invalid input exception with corresponding error message.
    public InvalidInputException(String invalidInput) {
        super("Invalid input: '" + invalidInput + "'. Try again.");
        this.invalidInput = invalidInput;
    }

    // EFFECTS: returns the invalid input string that triggered the exception.
    public String getInvalidInput() {
        return this.invalidInput;
    }
}
