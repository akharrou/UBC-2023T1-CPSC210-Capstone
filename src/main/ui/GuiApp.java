package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import model.InvalidInputException;
import org.json.JSONObject;

import model.FinancialAccount;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;

// Represents the GUI form of the application.
public class GuiApp {

    protected static final Integer MIN_FRAME_WIDTH = 550;
    protected static final Integer MIN_FRAME_HEIGHT = 600;
    protected static final String DATA_DIR = "./data/";

    protected static LaunchScreen launchScreen;
    protected static LoginScreen loginScreen;
    protected static AccountScreen accountScreen;

    protected static FinancialAccount account;

    // EFFECTS: constructs an instance of the GUI form of the application.
    public GuiApp() {
        GuiApp.launchScreen = new LaunchScreen();
    }

    // MODIFIES: this
    // EFFECTS: performs login, loading in the user's previously saved financial account data.
    //          IF: accountID is invalid, or its corresponding data file on disk isn't found
    //              will throw an 'InvalidInputException'.
    protected static void login(String accountID)
            throws InvalidInputException {
        if (accountID.length() < 3) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input '" + accountID + "': must be >2 characters. Try again.\"",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new InvalidInputException();
        }
        List<File> dirListing = Arrays.asList((new File(DATA_DIR)).listFiles(File::canRead));
        String dataFilepath = dirListing.stream().map(File::getName)
                .filter(pathname -> pathname.startsWith(accountID))
                .findFirst().orElse(null);
        if (dataFilepath == null) {
            JOptionPane.showMessageDialog(null,
                    "Login failed. Account not found.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new InvalidInputException();
        } else {
            try {
                GuiApp.account = new FinancialAccount(new JSONObject(Reader.read(DATA_DIR + dataFilepath)));
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Login failed.", "Error", JOptionPane.ERROR_MESSAGE);
                throw new InvalidInputException();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new financial account based on given information and logs into it.
    //          IF: firstname, lastname or netTargetCashflow is invalid will throw an 'InvalidInputException'.
    protected static void register(String firstname, String lastname, String netTargetCashflow)
            throws InvalidInputException {
        if (firstname.isEmpty() || lastname.isEmpty() || netTargetCashflow.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Registration failed. Missing an input field.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new InvalidInputException();
        }
        GuiApp.account = new FinancialAccount(firstname, lastname);
        GuiApp.editGoal(netTargetCashflow);
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved financial account from disk file.
    protected static void load(String accountFilepath) {
        try {
            GuiApp.account = new FinancialAccount(new JSONObject(Reader.read(accountFilepath)));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Loading failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: saves/writes currently logged-in financial account data to disk file, as JSON.
    //          the financial account can subsequently be loaded back from said [JSON] file.
    protected static void save() {
        try {
            Writer.write(GuiApp.account, DATA_DIR + GuiApp.account.getID() + ".json");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Saving failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: edits the target cashflow goal of the curently logged-in financial to the new input target.
    //          IF: netTargetCashflow is invalid (empty or non-numeric) will throw an 'InvalidInputException'.
    protected static void editGoal(String newTargetNetCashflow)
            throws InvalidInputException {
        if (newTargetNetCashflow.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Edit failed. Missing input field.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new InvalidInputException();
        }
        try {
            GuiApp.account.setTargetNetCashflow(Double.parseDouble(newTargetNetCashflow));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Edit failed.", "Error", JOptionPane.ERROR_MESSAGE);
            throw new InvalidInputException();
        }
    }
}
