package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import model.FinancialAccount;

public class App {

    boolean appIsRunning;
    boolean loggedIn;
    private Scanner scanner;
    private FinancialAccount account;

    // init screen commands
    private static final String LOGIN_COMMAND = "l";
    private static final String REGISTER_COMMAND = "r";
    private static final String QUIT_COMMAND = "q";
    // login screen
    private static final String LOAD_SESSION_COMMAND = "l";
    private static final String RESET_SESSION_COMMAND = "r";
    // action screen
    private static final String ADD_INFLOW_COMMAND = "i";
    private static final String ADD_OUTFLOW_COMMAND = "o";
    private static final String GET_SUMMARY_COMMAND = "s";
    private static final String EDIT_GOAL_COMMAND = "e";
    private static final String LOGOUT_COMMAND = "l";
    // login screen
    private static final String LOGOUT_W_SAVE_COMMAND = "y";
    private static final String LOGOUT_WO_SAVE_COMMAND = "n";

    // EFFECTS: initializes application.
    //          main loop handles all user interactions.
    //          terminates application.
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

    // MODIFIES: !TODO
    // EFFECTS: !TODO
    private void login() {
        register(); // !TODO
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO creates and initializes account
    private void register() {
        try {
            clearScreen();
            System.out.print("Register:" + "\n" + "\n  First: ");
            String first = this.scanner.next();
            System.out.print("  Last: ");
            String last = this.scanner.next();
            System.out.print("\nWelcome " + first + " " + last + ".\nOne last step, set your financial goal !\n");
            double targetNetCashflow;
            while (true) {
                try {
                    System.out.print("\n  Target Cashflow: ");
                    targetNetCashflow = Math.abs(Double.parseDouble(this.scanner.next()));
                    break;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid format: must be numeric input.");
                }
            }
            this.account = new FinancialAccount(first, last, targetNetCashflow);
        } catch (Exception e) {
            this.account = null;
            System.err.println("Account creation failed, aborting.");
        }
        this.loggedIn = (this.account != null);
    }

    // MODIFIES: this
    // EFFECTS: sets app in quit state in preparation of app termination.
    private void quit() {
        this.appIsRunning = false;
        this.loggedIn = false;
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO
    private void logout() {
        System.out.print("Would you like to save your session before leaving ? (Y/n) ");
        processLogoutScreenInput(this.scanner.next().toLowerCase());
        this.loggedIn = false;
    }

    // MODIFIES: !TODO
    // EFFECTS: !TODO
    private void processLogoutScreenInput(String input) {
        while (true) {
            if (input.equals(LOGOUT_W_SAVE_COMMAND) || input.equals("")) {
                System.out.print("Saving session... ");
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

    private void processActionScreenInput(String input) {
        if (input.equals(ADD_INFLOW_COMMAND) || input.equals(ADD_OUTFLOW_COMMAND)) {
            try {
                this.addEntry(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (input.equals(GET_SUMMARY_COMMAND)) {
            this.clearScreen();
            System.out.println(this.account.repr());
            System.out.print("\nPress 'Enter' to continue. ");
            this.scanner.next();
        } else if (input.equals(EDIT_GOAL_COMMAND)) {
            editTargetNetCashflow();
        } else if (input.equals(LOGOUT_COMMAND)) {
            logout();
        } else {
            System.out.print("Invalid input.\nPress 'Enter' to try again. ");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) { /* ignore */ }
            this.moveUpNLines(1);
            this.eraseLine();
        }
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

                this.account.addTransaction(((input.equals(ADD_INFLOW_COMMAND)) ? amount : -amount), description);
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

    private void displayLoginScreen01() {
        System.out.print(
                "\nLogin:"
                + "\n"
                + "\n  Identifier: "
        );
    }

    private void displayLoginScreen02() {
        System.out.print(
                "\nLogin:"
                + "\n"
                + "\n  (" + LOAD_SESSION_COMMAND + ") load session"
                + "\n  (" + RESET_SESSION_COMMAND + ") reset session"
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
