package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import model.FinancialAccount;
import model.InvalidInputException;
import org.json.JSONObject;
import persistence.Reader;
import persistence.Writer;
import ui.LaunchScreen;

import javax.swing.*;

public class Main {

    protected static final Integer MIN_FRAME_WIDTH = 550;
    protected static final Integer MIN_FRAME_HEIGHT = 600;
    protected static final String DATA_DIR = "./data/";

    protected static LaunchScreen launchScreen;
    protected static LoginScreen loginScreen;
    protected static AccountScreen accountScreen;

    protected static FinancialAccount account;

    public static void main(String[] args) throws IOException {
        Main.launchScreen = new LaunchScreen();
    }

    // MODIFIES: this
    // EFFECTS: prompts for user sign-in information
    //          ∧ (optionally) loads saved financial account corresponding to sign-in info
    protected static void login(String accountID) {
        List<File> dirListing = Arrays.asList((new File(DATA_DIR)).listFiles(File::canRead));
        String dataFilepath = dirListing.stream().map(File::getName)
                .filter(pathname -> pathname.startsWith(accountID))
                .findFirst().orElse(null);
        if (dataFilepath == null) {
            System.out.print("\nAccount not found. You can register or try again.");
        } else {
            try {
                Main.account = new FinancialAccount(new JSONObject(Reader.read(DATA_DIR + dataFilepath)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts for user sign-up information
    //          ∧ creates financial account based on given information
    protected static void register(String firstname, String lastname, Double netTargetCashflow) {
        Main.account = new FinancialAccount(firstname, lastname);
        Main.editGoal(netTargetCashflow);
    }

    // MODIFIES: this
    // EFFECTS: loads previously saved financial account from disk file
    protected static void load(String accountFilepath) {
        try {
            Main.account = new FinancialAccount(new JSONObject(Reader.read(accountFilepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // EFFECTS: saves/writes currently logged-in financial account data to disk file, as JSON.
    //          the financial account can subsequently be loaded back from said [JSON] file.
    protected static void save() {
        try {
            Writer.write(Main.account, DATA_DIR + Main.account.getID() + ".json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: modifies the target cashflow goal of the curently logged-in financial
    //          account to that specified by user input.
    protected static void editGoal(Double newTargetNetCashflow) {
        Main.account.setTargetNetCashflow(newTargetNetCashflow);
    }
}
