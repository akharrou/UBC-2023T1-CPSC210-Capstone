package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.json.JSONObject;

import model.FinancialAccount;
import model.InvalidInputException;
import persistence.Reader;
import persistence.Writer;

// Financial tracker application.
// !TODO: double check all is tested
public class App {

    private Scanner scanner;
    private boolean keepRunning;
    private FinancialAccount account;

    private static final String DATA_DIR = "./data/";

    // home-screen commands
    private static final String LOGIN_COMMAND = "l";
    private static final String REGISTER_COMMAND = "r";
    private static final String QUIT_COMMAND = "q";

    // action-screen commands
    private static final String ADD_INFLOW_COMMAND = "i";
    private static final String ADD_OUTFLOW_COMMAND = "o";
    private static final String GET_SUMMARY_COMMAND = "s";
    private static final String EDIT_GOAL_COMMAND = "e";
    private static final String RESET_ACCOUNT_COMMAND = "r";
    private static final String LOGOUT_COMMAND = "l";

    // EFFECTS: constructs, initializes, runs and terminates a new instance of the application.
    public App() {
        this.keepRunning = true;
        this.scanner = (new Scanner(System.in)).useDelimiter("\n");
        try {
            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("\nQuitting application... ");
        this.sleep(250);
    }

    // REQUIRES: non-null & initialized scanner
    // MODIFIES: this
    // EFFECTS: initializes, runs the user input handling main loop of, and terminates the application.
    public void run() {
        while (this.keepRunning) {
            this.displayHomeScreen();
            this.processHomeScreenInput();
            while (this.isLoggedIn()) {
                this.displayActionScreen();
                this.processActionScreenInput();
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    // MODIFIES: this
    // EFFECTS: prompts for user sign-up information
    //          ∧ creates financial account based on given information
    private void register() {
        while (true) {
            try {
                System.out.print("\033\143" + "Register:" + "\n" + "\n  First: ");
                String first = this.scanner.next();
                System.out.print("  Last: ");
                String last = this.scanner.next();
                if (first.length() < 1 || last.length() < 1) {
                    throw new InvalidInputException(first + " " + last);
                }
                System.out.print("\033\143" + "Creating account... ");
                this.account = new FinancialAccount(first, last);
                this.sleep(1500);
                this.editGoal();
                break;
            } catch (InvalidInputException e) {
                System.err.print("\nInvalid input '" + e.getInvalidInput()
                                + "': first and last names must be >0 characters.");
                this.sleep(2000);
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    // MODIFIES: this
    // EFFECTS: prompts for user sign-in information
    //          ∧ (optionally) loads saved financial account corresponding to sign-in info
    private void login() {
        while (true) {
            try {
                System.out.print("\033\143" + "Login:" + "\n" + "\n  Account-ID: ");
                String accID = this.scanner.next();
                if (accID.length() < 3) {
                    throw new InvalidInputException(accID);
                }
                List<File> dirListing = Arrays.asList((new File(DATA_DIR)).listFiles(File::canRead));
                String dataFilepath = dirListing.stream().map(File::getName)
                        .filter(pathname -> pathname.startsWith(accID))
                        .findFirst().orElse(null);
                if (dataFilepath == null) {
                    System.out.print("\nAccount not found. You can register or try again.");
                    sleep(2000);
                } else {
                    resume(dataFilepath);
                }
                break;
            } catch (InvalidInputException e) {
                System.err.print("\nInvalid input '" + e.getInvalidInput() + "': must be >2 characters. Try again.");
                this.sleep(2000);
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    //           ∧ logged-in ≡ non-null account
    //           ∧ non-null input file (dataFilepath)
    // MODIFIES: this
    // EFFECTS: resumes application from a previously saved session or launches a fresh session,
    //          according to user input.
    //          if user responds affirmatively → loads saved user profile AND ledger
    //          if user responds negatively → only loads saved user profile
    private void resume(String dataFilepath) {
        while (true) {
            try {
                System.out.print("\033\143" + "Resume from last saved session ? (Y/n) ");
                String input = this.scanner.next();
                if (input.equalsIgnoreCase("y") || input.equals("")) {
                    System.out.print("\033\143" + "Loading session... ");
                    load(DATA_DIR + dataFilepath);
                    this.sleep(1500);
                } else if (input.equalsIgnoreCase("n")) {
                    System.out.print("\033\143" + "Preparing new session... ");
                    load(DATA_DIR + dataFilepath);
                    this.account.reset();
                    this.sleep(1500);
                    this.editGoal();
                } else {
                    throw new InvalidInputException(input);
                }
                break;
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
                this.sleep(1500);
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    // MODIFIES: this
    // EFFECTS: logs out of currently logged-in financial account, either saving the currently
    //          logged-in financial account data in the process or not, according to user input.
    private void logout() {
        while (true) {
            System.out.print("\033\143" + "Save session ? (Y/n) ");
            String input = this.scanner.next();
            try {
                if (input.equalsIgnoreCase("y") || input.equals("")) {
                    System.out.print("\033\143" + "Saving session... ");
                    save();
                } else if (input.equalsIgnoreCase("n")) {
                    System.out.print("\033\143" + "Discarding session... ");
                } else {
                    throw new InvalidInputException(input);
                }
                sleep(1500);
                break;
            } catch (InvalidInputException e) {
                System.err.print(e.getMessage());
                sleep(1500);
            }
        }
        this.account = null;
    }

    // REQUIRES: logged-in ≡ non-null account
    // EFFECTS: saves/writes currently logged-in financial account data to disk file, as JSON.
    //          the financial account can subsequently be loaded back from said [JSON] file.
    private void save() {
        try {
            Writer.write(this.account, DATA_DIR + this.account.getID() + ".json");
        } catch (FileNotFoundException e) {
            System.err.print(e.getMessage());
            this.sleep(1500);
        }
    }

    // REQUIRES: input file path (accountFilepath) must be the path to an existing ∧ regular ∧ readable disk file
    // MODIFIES: this
    // EFFECTS: loads previously saved financial account from disk file
    private void load(String accountFilepath) {
        try {
            this.account = new FinancialAccount(new JSONObject(Reader.read(accountFilepath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: logouts and sets application in termination state.
    private void quit() {
        this.account = null;
        this.keepRunning = false;
    }

    // REQUIRES: non-null & initialized scanner
    //           ∧ logged-in ≡ non-null account
    // MODIFIES: this
    // EFFECTS: modifies the target cashflow goal of the curently logged-in financial
    //          account to that specified by user input.
    private void editGoal() {
        while (true) {
            try {
                System.out.print("\033\143" + "Target Cashflow: ");
                this.account.setTargetNetCashflow(Double.parseDouble(this.scanner.next()));
                break;
            } catch (NumberFormatException e) {
                System.err.print("Invalid input: must be numeric. ");
                this.sleep(1500);
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    //           ∧ logged-in ≡ non-null account
    // MODIFIES: this
    // EFFECTS: resets (drops ledger of) the currently logged-in financial account or aborts (according to user input).
    private void reset() {
        while (true) {
            try {
                System.out.print(String.format(
                        "\033\143" // clear screen
                        + "Reseting your account will delete all entries currently in your ledger."
                        + "\nProceed anyways ? (y/N) "
                ));
                String input = this.scanner.next();
                if (input.equalsIgnoreCase("y")) {
                    System.out.print("\033\143" + "Reseting account... ");
                    this.account.reset();
                } else if (input.equalsIgnoreCase("n") || input.equals("")) {
                    System.out.print("\033\143" + "Reset aborted.");
                } else {
                    throw new InvalidInputException(input);
                }
                this.sleep(1500);
                break;
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
                this.sleep(1500);
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    //           ∧ logged-in ≡ non-null account
    // EFFECTS: generates and displays to console a summary report of the currently logged-in financial account.
    private void summarize() {
        System.out.print(String.format(
                "\033\143"
                + this.account.consoleRepr()
                + "\n"
                + "\nPress 'Enter' to continue. "
        ));
        this.scanner.next();
    }

    // REQUIRES: non-null & initialized scanner
    //           ∧ non-null lowercased input string
    //           ∧ logged-in ≡ non-null account
    // MODIFIES: this
    // EFFECTS: processes user input and creates & records a financial inflow or outflow entry to the ledger
    //          of the currently logged-in financial account, according to said processed user input.
    //          if add inflow command was triggered → financial entry will be an inflow
    //          else → will be an outflow
    private void processFinancialEntry(String input) {
        while (true) {
            try {
                System.out.print("\r" + ((input.equals(ADD_INFLOW_COMMAND)) ? "Inflow " : "Outflow ") + "amount: ");
                double amount = Math.abs(Double.parseDouble(this.scanner.next()));
                System.out.print("Description: ");
                String description = this.scanner.next();
                this.account.recordFinancialEntry(((input.equals(ADD_INFLOW_COMMAND)) ? amount : -amount), description);
                System.out.print("Transaction successfully recorded.");
                this.sleep(750);
                break;
            } catch (NumberFormatException e) {
                System.err.print("Invalid input: must be numeric. ");
                this.sleep(750);
                this.eraseLine();
                this.moveUpNLines(1);
                this.eraseLine();
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    // MODIFIES: this
    // EFFECTS: processes home-screen user-input
    private void processHomeScreenInput() {
        while (true) {
            String input = this.scanner.next();
            try {
                if (input.equalsIgnoreCase(LOGIN_COMMAND)) {
                    login();
                } else if (input.equalsIgnoreCase(REGISTER_COMMAND)) {
                    register();
                } else if (input.equalsIgnoreCase(QUIT_COMMAND)) {
                    quit();
                } else {
                    throw new InvalidInputException(input);
                }
                break;
            } catch (InvalidInputException e) {
                System.err.print(e.getMessage());
                this.sleep(1500);
                this.eraseLine();
                this.moveUpNLines(1);
                this.eraseLine();
                System.out.print("\r> ");
            }
        }
    }

    // REQUIRES: non-null & initialized scanner
    // MODIFIES: this
    // EFFECTS: processes action-screen user-input
    private void processActionScreenInput() {
        while (true) {
            String input = this.scanner.next();
            try {
                if (input.equalsIgnoreCase(ADD_INFLOW_COMMAND) || input.equalsIgnoreCase(ADD_OUTFLOW_COMMAND)) {
                    this.processFinancialEntry(input.toLowerCase());
                } else if (input.equalsIgnoreCase(GET_SUMMARY_COMMAND)) {
                    this.summarize();
                } else if (input.equalsIgnoreCase(EDIT_GOAL_COMMAND)) {
                    this.editGoal();
                } else if (input.equalsIgnoreCase(RESET_ACCOUNT_COMMAND)) {
                    this.reset();
                } else if (input.equalsIgnoreCase(LOGOUT_COMMAND)) {
                    this.logout();
                } else {
                    throw new InvalidInputException(input);
                }
                break;
            } catch (InvalidInputException e) {
                System.err.print(e.getMessage());
                this.sleep(1500);
                System.out.print("\033[2K" + "\033[1A" + "\033[2K" + "\r> ");
            }
        }
    }

    // EFFECTS: displays home-menu screen
    private void displayHomeScreen() {
        System.out.print(
                "\033\143" // clear screen
                + "Welcome to CPSC210P01-Financial-Tracker !"
                + "\n"
                + "\n  (" + LOGIN_COMMAND + ") login"
                + "\n  (" + REGISTER_COMMAND + ") register"
                + "\n  (" + QUIT_COMMAND + ") quit"
                + "\n"
                + "\n> "
        );
    }

    // REQUIRES: logged-in ≡ non-null account
    // EFFECTS: displays action-menu screen
    private void displayActionScreen() {
        System.out.print(String.format(
                "\033\143" // clear screen
                + "Datetime: %s"
                + "\nAccount: %s"
                + "\nOwner: %s"
                + "\n\nPresent Net Cashflow: $%,.2f"
                + "\nTarget Net Cashflow: $%,.2f"
                + "\n\nActions:"
                + "\n\n  (" + ADD_INFLOW_COMMAND + ") record inflow"
                + "\n  (" + ADD_OUTFLOW_COMMAND + ") record outflow"
                + "\n  (" + GET_SUMMARY_COMMAND + ") get summary"
                + "\n  (" + EDIT_GOAL_COMMAND + ") edit target"
                + "\n  (" + RESET_ACCOUNT_COMMAND + ") reset acccount"
                + "\n  (" + LOGOUT_COMMAND + ") logout"
                + "\n\n> ",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()),
                this.account.getID(),
                this.account.getFirstname() + " " + this.account.getLastname(),
                this.account.getPresentNetCashflow(),
                this.account.getTargetNetCashflow()
        ));
    }

    // EFFECTS: returns true if an account is currently logged-in the application, and false otherwise.
    private boolean isLoggedIn() {
        return (this.account != null);
    }

    // REQUIRES: non-negative nlines
    // EFFECTS: moves cursor up a given number of lines.
    private void moveUpNLines(int nlines) {
        System.out.print(String.format("\033[%dA", nlines));
    }

    // EFFECTS: erases current line contents.
    private void eraseLine() {
        System.out.print("\033[2K");
    }

    // REQUIRES: non-negative milliseconds
    // EFFECTS: sleeps for specified number of seconds.
    // CITATIONS:
    //  [1]: https://stackoverflow.com/a/24104427
    //  [2]: https://stackoverflow.com/a/24104332
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) { /* ignore */ }
    }
}
