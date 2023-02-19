package ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import model.FinancialAccount;

public class App {

    boolean loggedIn;
    private Scanner scanner;
    private FinancialAccount account;

    private static final String ADD_INFLOW_COMMAND = "a";
    private static final String ADD_OUTFLOW_COMMAND = "b";
    private static final String GET_SUMMARY_COMMAND = "c";
    private static final String LOGOUT_COMMAND = "q";

    // MODIFIES: this
    // EFFECTS: initializes app
    private void init() {
        try {
            this.account = new FinancialAccount();
        } catch (Exception e) {
            this.account = null;
            System.err.println("Account creation failed, aborting.");
        }
        this.loggedIn = (this.account != null);
        this.scanner = (new Scanner(System.in)).useDelimiter("\n");
    }

    // EFFECTS: initializes application.
    //          main loop handles all user interactions.
    //          terminates application.
    public void run() {
        this.init();
        while (this.loggedIn) {
            System.out.print("\033\143"); // clear screen
            this.displayHeader();
            this.displayMenu();
            this.processInput(this.scanner.next().toLowerCase());
        }
        this.terminate();
    }

    private void terminate() {
        System.out.println("\nLogging out.");
    }

    private void displayHeader() {
        System.out.println("Datetime: "
                + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        System.out.println("Logged in as: " + this.account.getFirstname() + " " + this.account.getLastname());
        System.out.println("Present Net Cashflow: " + this.account.getPresentNetCashflow());
        System.out.println("Target Net Cashflow: " + this.account.getTargetNetCashflow());
    }

    private void displayMenu() {
        System.out.println("\nActions:\n");
        System.out.println("  (" + ADD_INFLOW_COMMAND + ") record inflow");
        System.out.println("  (" + ADD_OUTFLOW_COMMAND + ") record outflow");
        System.out.println("  (" + GET_SUMMARY_COMMAND + ") get summary");
        System.out.println("  (" + LOGOUT_COMMAND + ") logout");
        System.out.println("");
        System.out.print("> ");
    }

    private void processInput(String input) {
        if (input.equals(ADD_INFLOW_COMMAND) || input.equals(ADD_OUTFLOW_COMMAND)) {
            try {
                this.doAddEntryCommand(input);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (input.equals(GET_SUMMARY_COMMAND)) {
            System.out.print("\033\143"); // clear screen
            System.out.println(this.account.getSummary());
            System.out.print("\nPress 'Enter' to continue. ");
            this.scanner.next();
        } else if (input.equals(LOGOUT_COMMAND)) {
            this.loggedIn = false;
        } else {
            System.out.print("Invalid input.\nPress 'Enter' to try again. ");
        }
    }

    private void doAddEntryCommand(String input)
            throws InterruptedException {
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
                System.err.println("Invalid format: must be numeric input");
            } finally {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(String.format("\033[%dA",1)); // Move up
                System.out.print("\033[2K"); // Erase line content
                System.out.print(String.format("\033[%dA",1)); // Move up
                System.out.print("\033[2K"); // Erase line content
            }
        }
    }
}
