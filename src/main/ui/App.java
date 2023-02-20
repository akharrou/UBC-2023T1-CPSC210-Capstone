package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.json.JSONObject;

import model.FinancialAccount;

// Financial tracker application.
public class App {

    boolean appIsRunning;
    boolean loggedIn;
    private Scanner scanner;
    private FinancialAccount account;

    private static final String DATA_DIR = "./data/";

    // init screen commands
    private static final String LOGIN_COMMAND = "l";
    private static final String REGISTER_COMMAND = "r";
    private static final String QUIT_COMMAND = "q";
    // action screen
    private static final String ADD_INFLOW_COMMAND = "i";
    private static final String ADD_OUTFLOW_COMMAND = "o";
    private static final String GET_SUMMARY_COMMAND = "s";
    private static final String EDIT_GOAL_COMMAND = "e";
    private static final String RESET_ACCOUNT_COMMAND = "r";
    private static final String LOGOUT_COMMAND = "l";
    // login screen
    private static final String LOGOUT_W_SAVE_COMMAND = "y";
    private static final String LOGOUT_WO_SAVE_COMMAND = "n";

    // EFFECTS: runs the application
    public App() {
        this.run();
    }

    // MODIFIES: this
    // EFFECTS: initializes, runs the user input handling main loop of, and terminates the application.
    public void run() {
        this.init();
        while (this.appIsRunning) {
            this.displayHomeScreen();
            this.processHomeScreenInput(this.scanner.next().toLowerCase());
            while (this.loggedIn) {
                this.displayActionScreen();
                this.processActionScreenInput(this.scanner.next().toLowerCase());
            }
        }
        this.terminate();
    }

    // MODIFIES: this
    // EFFECTS: initializes app
    private void init() {
        this.appIsRunning = true;
        this.loggedIn = false;
        this.scanner = (new Scanner(System.in)).useDelimiter("\n");
    }

    private void terminate() {
        System.out.println("\nLogging out.");
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO creates and initializes account
    private void register() {
        try {
            this.clearScreen();
            System.out.print("Register:" + "\n" + "\n  First: ");
            String first = this.scanner.next();
            System.out.print("  Last: ");
            String last = this.scanner.next();
            System.out.print("\nWelcome " + first + " " + last + ".\nOne last step, set your financial goal !\n");
            // !TOFIX: do something if first and/or last are empty strings
            double targetNetCashflow;
            while (true) {
                try {
                    System.out.print("\n  Target Cashflow: ");
                    targetNetCashflow = Math.abs(Double.parseDouble(this.scanner.next()));
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid format: must be numeric input.");
                    // !TOFIX doesn't erase lines
                }
            }
            this.account = new FinancialAccount(first, last, targetNetCashflow);
        } catch (Exception e) {
            this.account = null;
            System.err.println("Account creation failed, aborting.");
        }
        this.loggedIn = (this.account != null);
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO
    private void login() {
        this.clearScreen();
        System.out.print("\nLogin:" + "\n" + "\n  Account-ID: ");
        String accID = this.scanner.next().toLowerCase();
        List<File> dirListing = Arrays.asList((new File(DATA_DIR)).listFiles(File::canRead));
        String dataFilepath = dirListing.stream()
                .map(File::getName)
                .filter(p -> p.startsWith(accID))
                .findFirst()
                .orElse(null);
        if (dataFilepath == null) {
            System.out.print("Account not found. You can register or try again.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) { /* ignore */ }
        } else {
            this.loggedIn = true;
            System.out.print("Resume from saved session ? (Y/n) ");
            String input = this.scanner.next().toLowerCase();
            if (input.equals("y") || input.equals("")) {
                System.out.print("Loading session... ");
                load(DATA_DIR + dataFilepath);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) { /* ignore */ }
            }
        }
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO
    private void logout() {
        System.out.print("Would you like to save your session before leaving ? (Y/n) ");
        processLogoutScreenInput(this.scanner.next().toLowerCase());
        this.loggedIn = false;
    }

    // CITATIONS:
    //  [1]: https://stackoverflow.com/a/2885224/13992057
    private void save() {
        try (PrintWriter pw = new PrintWriter(new File(DATA_DIR + this.account.getID() + ".json"))) {
            pw.print(this.account.jsonRepr().toString(4));
        } catch (FileNotFoundException e) {
            System.out.print("Save failed. Sorry your data will be lost.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e1) { /* ignore */ }
        }
    }

    // REQUIRES: accountDataFilepath must be a regular readable file
    // EFFECTS: !TODO
    private void load(String accountFilepath) {
        try {
            this.account =
                new FinancialAccount(
                    new JSONObject(
                        Files.lines(Paths.get(accountFilepath), StandardCharsets.UTF_8)
                        .collect(Collectors.joining())
                    )
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets app in quit state in preparation of app termination.
    private void quit() {
        this.appIsRunning = false;
        this.loggedIn = false;
    }

    // EFFECTS: !TODO
    private void editTargetNetCashflow() {
        while (true) {
            clearScreen();
            try {
                System.out.print("New Target Cashflow: ");
                this.account.setTargetNetCashflow(Double.parseDouble(this.scanner.next()));
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid format: must be numeric input.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) { /* ignore */ }
                this.moveUpNLines(1);
                this.eraseLine();
            }
        }
    }

    // EFFECTS: !TODO
    private void addEntry(String input) {
        while (true) {
            try {
                System.out.print(((input.equals(ADD_INFLOW_COMMAND)) ? "Inflow " : "Outflow ") + "amount: ");
                double amount = Math.abs(Double.parseDouble(this.scanner.next()));

                System.out.print("Description: ");
                String description = this.scanner.next();

                this.account.recordLedgerEntry(((input.equals(ADD_INFLOW_COMMAND)) ? amount : -amount), description);
                System.out.print("Transaction successfully recorded.");
                break;
            } catch (NumberFormatException e) {
                System.err.println("Invalid format: must be numeric input.");
            } finally {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) { /* ignore */ }
                this.moveUpNLines(1);
                this.eraseLine();
                this.moveUpNLines(1);
                this.eraseLine();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: !TODO creates and initializes account
    private void processHomeScreenInput(String input) {
        if (input.equals(LOGIN_COMMAND)) {
            login();
        } else if (input.equals(REGISTER_COMMAND)) {
            register();
        } else if (input.equals(QUIT_COMMAND)) {
            quit();
        } else {
            System.out.print("Invalid input. Try again.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) { /* ignore */ }
        }
    }

    private void processActionScreenInput(String input) {
        if (input.equals(ADD_INFLOW_COMMAND) || input.equals(ADD_OUTFLOW_COMMAND)) {
            try {
                this.addEntry(input);
            } catch (Exception e) {
                System.out.print("An error occurred.\nTry again. ");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e1) { /* ignore */ }
            }
        } else if (input.equals(GET_SUMMARY_COMMAND)) {
            this.clearScreen();
            System.out.println(this.account.consoleRepr());
            System.out.print("\nPress 'Enter' to continue. ");
            this.scanner.next();
        } else if (input.equals(EDIT_GOAL_COMMAND)) {
            editTargetNetCashflow();
        } else if (input.equals(RESET_ACCOUNT_COMMAND)) {
            register(); // NOTE: could just have a this.account.resetLedger();
        } else if (input.equals(LOGOUT_COMMAND)) {
            logout();
        } else {
            System.out.print("Invalid input.\nTry again. ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) { /* ignore */ }
            this.moveUpNLines(1);
            this.eraseLine();
        }
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO
    private void processLogoutScreenInput(String input) {
        while (true) {
            // !TOFIX bad input makes it loop forever; input = this.scanner.next().toLowerCase();
            if (input.equals(LOGOUT_W_SAVE_COMMAND) || input.equals("")) {
                System.out.print("Saving session... ");
                save();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) { /* ignore */ }
                break;
            } else if (input.equals(LOGOUT_WO_SAVE_COMMAND)) {
                System.out.print("Discarding session... ");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) { /* ignore */ }
                break;
            } else {
                System.out.print("Invalid input. Try again. ");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) { /* ignore */ }
                this.moveUpNLines(1);
                this.eraseLine();
            }
        }
    }

    private void displayHomeScreen() {
        this.clearScreen();
        System.out.print(
                "Welcome to CPSC210P01-Financial-Tracker !"
                + "\n"
                + "\n  (" + LOGIN_COMMAND + ") login"
                + "\n  (" + REGISTER_COMMAND + ") register"
                + "\n  (" + QUIT_COMMAND + ") quit"
                + "\n"
                + "\n> "
        );
    }

    private void displayHeader() {
        System.out.print(String.format(
                "Datetime: %1$s"
                + "\nLogged in as: %2$s"
                + "\n"
                + "\nPresent Net Cashflow: $%3$,.2f"
                + "\nTarget Net Cashflow: $%4$,.2f",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()),
                this.account.getFirstname() + " " + this.account.getLastname(),
                this.account.getPresentNetCashflow(),
                this.account.getTargetNetCashflow()
        ));
    }

    private void displayActionScreen() {
        this.clearScreen();
        this.displayHeader();
        System.out.print(
                "\n\nActions:"
                + "\n\n  (" + ADD_INFLOW_COMMAND + ") record inflow"
                + "\n  (" + ADD_OUTFLOW_COMMAND + ") record outflow"
                + "\n  (" + GET_SUMMARY_COMMAND + ") get summary"
                + "\n  (" + EDIT_GOAL_COMMAND + ") edit target"
                + "\n  (" + RESET_ACCOUNT_COMMAND + ") reset acccount"
                + "\n  (" + LOGOUT_COMMAND + ") logout"
                + "\n"
                + "\n> "
        );
    }

    // EFFECTS: clear the console screen
    private void clearScreen() {
        System.out.print("\033\143");
    }

    // EFFECTS: move cursor up given number of lines.
    private void moveUpNLines(int nlines) {
        System.out.print(String.format("\033[%dA", nlines));
    }

    // EFFECTS: erase current line contents.
    private void eraseLine() {
        System.out.print("\033[2K");
    }
}
