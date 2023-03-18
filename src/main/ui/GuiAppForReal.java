package ui;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.json.JSONObject;

import model.FinancialAccount;
import model.InvalidInputException;
import persistence.Reader;

public class GuiAppForReal extends JFrame {

    private static final String DATA_DIR = "./data/";

    private FinancialAccount account;

    private JButton addInflowButton;
    private JButton addOutflowButton;
    private JTable ledgerTable;
    private JTextField searchTextField;
    private JPanel mainPanel;
    private JLabel fintrakLabel;
    private JLabel accountNameLabel;
    private JLabel accountIdLabel;

    public GuiAppForReal() {
        super("Fintrak");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0xFAFAFA));
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.pack();
        this.run();
    }

    private void run() {
        createTable();
        load("./data/42e042f4-90b1-4dbc-b11a-2f818a49adf1.json");
        this.accountNameLabel.setText(account.getFirstname() + " " + account.getLastname());
        this.accountIdLabel.setText("ID: " + account.getID().toString().substring(0,7));

        DefaultTableModel model = (DefaultTableModel) ledgerTable.getModel();
        model.setRowCount(0);
        account.getLedger().fetch(null).stream().forEach(elem -> {
            model.addRow(new Object[] {elem.getID(), elem.getCreated(), elem.getDescription(), elem.getAmount()});
        });
    }

    private void createTable() {
        ledgerTable.setModel(new DefaultTableModel(null, new String[] {"#", "Created", "Description", "Amount"}));
        TableColumnModel columns = ledgerTable.getColumnModel();
        columns.getColumn(0).setMinWidth(40);
        columns.getColumn(0).setMaxWidth(40);
        columns.getColumn(1).setMinWidth(150);
        columns.getColumn(1).setMaxWidth(160);
        columns.getColumn(2).setMinWidth(100);
        columns.getColumn(3).setMinWidth(50);
        columns.getColumn(3).setMaxWidth(160);
        DefaultTableCellRenderer cellLeftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellCenterRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRightRenderer = new DefaultTableCellRenderer();
        cellLeftRenderer.setHorizontalAlignment(JLabel.LEFT);
        cellCenterRenderer.setHorizontalAlignment(JLabel.CENTER);
        cellRightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columns.getColumn(0).setCellRenderer(cellCenterRenderer);
        columns.getColumn(2).setCellRenderer(cellLeftRenderer);
        columns.getColumn(3).setCellRenderer(cellRightRenderer);
    }

    // MODIFIES: this
    // EFFECTS: prompts for user sign-in information
    //          ∧ (optionally) loads saved financial account corresponding to sign-in info
    private void login() {
        while (true) {
            try {
                System.out.print("\033\143" + "Login:" + "\n" + "\n  Account-ID: ");
                String accID = "42e042f4";
                if (accID.length() < 3) {
                    throw new InvalidInputException(accID);
                }
                List<File> dirListing = Arrays.asList((new File(DATA_DIR)).listFiles(File::canRead));
                String dataFilepath = dirListing.stream().map(File::getName)
                        .filter(pathname -> pathname.startsWith(accID))
                        .findFirst().orElse(null);
                if (dataFilepath == null) {
                    System.out.print("\nAccount not found. You can register or try again.");
                } else {
                    resume(dataFilepath);
                }
                break;
            } catch (InvalidInputException e) {
                System.err.print("\nInvalid input '" + e.getInvalidInput() + "': must be >2 characters. Try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: resumes application from a previously saved session or launches a fresh session,
    //          according to user input.
    //          if user responds affirmatively → loads saved user profile AND ledger
    //          if user responds negatively → only loads saved user profile
    private void resume(String dataFilepath) {
        while (true) {
            try {
                System.out.print("\033\143" + "Resume from last saved session ? (Y/n) ");
                String input = "y";
                if (input.equalsIgnoreCase("y") || input.equals("")) {
                    System.out.print("\033\143" + "Loading session... ");
                    load(DATA_DIR + dataFilepath);
                } else if (input.equalsIgnoreCase("n")) {
                    System.out.print("\033\143" + "Preparing new session... ");
                    load(DATA_DIR + dataFilepath);
                    this.account.reset();
                    this.editGoal();
                } else {
                    throw new InvalidInputException(input);
                }
                break;
            } catch (InvalidInputException e) {
                System.err.println(e.getMessage());
            }
        }
    }


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
    // EFFECTS: modifies the target cashflow goal of the curently logged-in financial
    //          account to that specified by user input.
    private void editGoal() {
        while (true) {
            try {
                System.out.print("\033\143" + "Target Cashflow: ");
                this.account.setTargetNetCashflow(12.34);
                break;
            } catch (NumberFormatException e) {
                System.err.print("Invalid input: must be numeric. ");
            }
        }
    }

}
