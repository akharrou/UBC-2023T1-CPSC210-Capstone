package ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.json.JSONObject;

import model.FinancialAccount;
import model.InvalidInputException;
import persistence.Reader;

import static java.lang.Thread.sleep;
import static ui.Main.*;

public class AccountScreen extends JFrame {

    private JButton addEntryButton;
    private JButton resetTableButton;
    private JTable ledgerTable;
    private TableRowSorter<DefaultTableModel> trs;
    private JTextField searchTextField;
    private JPanel mainPanel;
    private JLabel fintrakLabel;
    private JLabel accountNameLabel;
    private JLabel accountIdLabel;
    private JButton searchButton;
    private JPanel footerPanel;
    private JPanel actionsPanel;
    private JPanel menubarPanel;
    private JPanel searchPanel;
    private JPanel headerPanel;
    private JPanel statsPanel;
    private JPanel bodyPanel;
    private JScrollPane ledgerScrollPane;
    private JPanel pncPanel;
    private JPanel tncPanel;
    private JPanel fsPanel;
    private JLabel pncLabel;
    private JLabel tncLabel;
    private JLabel fsLabel;
    private JButton logoutButton;

    public AccountScreen() {
        super("Fintrac");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0xFAFAFA));
        this.setContentPane(mainPanel);
        this.setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.createUIComponents();
        this.pack();
        this.setVisible(true);
        this.updateScreen();
    }

    public void updateScreen() {
        this.updateHeader();
        this.updateBody();
        this.updateFooter();
    }

    private void updateHeader() {
        this.accountNameLabel.setText(Main.account.getFirstname() + " " + Main.account.getLastname());
        this.accountIdLabel.setText(Main.account.getID().toString().substring(0,7));
    }

    private void updateBody() {
        // CITE: <https://www.youtube.com/watch?v=teSBvFy9NH8>
        DefaultTableModel model = (DefaultTableModel) ledgerTable.getModel();
        model.setRowCount(0);
        Main.account.getLedger().stream().forEach(elem -> {
            model.addRow(new Object[] {elem.getID(), elem.getCreated(), elem.getDescription(), elem.getAmountRepr()});
        });
        searchButton.doClick();
    }

    private void updateFooter() {
        String pnc = String.format("Present Net Cashflow: %s $%,.2f",
                Main.account.getPresentNetCashflow() >= 0 ? "" : "–",
                Main.account.getPresentNetCashflow()
        );
        String tnc = String.format("Target Net Cashflow: %s $%,.2f",
                Main.account.getTargetNetCashflow() >= 0 ? "" : "–",
                Main.account.getTargetNetCashflow()
        );
        String fs = String.format("Financial Standing: %s",
                Main.account.getPresentNetCashflow() > Main.account.getTargetNetCashflow() ? "Good" : "Bad"
        );
        this.pncLabel.setText(pnc);
        this.tncLabel.setText(tnc);
        this.fsLabel.setText(fs);
    }

    private void createUIComponents() {
        setupTable();
        setupActions();
    }

    private void setupActions() {
        addEntryButton.addActionListener(event -> {
            new EntryDialog();
        });
        resetTableButton.addActionListener(event -> {
            Main.account.getLedger().clear();
            updateScreen();
        });
        logoutButton.addActionListener(event -> {
            int response = JOptionPane.showConfirmDialog(null, "Save session?", "Title", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                Main.save();
            }
            Main.account = null;
            this.setVisible(false);
            Main.loginScreen = new LoginScreen();
            this.dispose();
        });
    }

    private void setupTable() {

        // CITE: <https://www.youtube.com/watch?v=3m1j3PiUeVI>
        ledgerTable.setModel(new DefaultTableModel(null, new String[] {"ID", "Created", "Description", "Amount"}));

        ledgerTable.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 12));
        ledgerTable.setRowHeight(20);

        DefaultTableCellRenderer cellLeftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellCenterRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRightRenderer = new DefaultTableCellRenderer();
        cellLeftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        cellCenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        // CITE: stackoverflow
        ledgerTable.getTableHeader().getColumnModel().getColumn(0).setHeaderRenderer(cellCenterRenderer);
        ledgerTable.getTableHeader().getColumnModel().getColumn(1).setHeaderRenderer(cellLeftRenderer);
        ledgerTable.getTableHeader().getColumnModel().getColumn(2).setHeaderRenderer(cellLeftRenderer);
        ledgerTable.getTableHeader().getColumnModel().getColumn(3).setHeaderRenderer(cellRightRenderer);

        // CITE: <https://www.youtube.com/watch?v=3m1j3PiUeVI>
        ledgerTable.getColumnModel().getColumn(0).setMinWidth(30);
        ledgerTable.getColumnModel().getColumn(0).setMaxWidth(30);
        ledgerTable.getColumnModel().getColumn(0).setCellRenderer(cellCenterRenderer);
        ledgerTable.getColumnModel().getColumn(1).setMinWidth(150);
        ledgerTable.getColumnModel().getColumn(1).setMaxWidth(150);
        ledgerTable.getColumnModel().getColumn(2).setMinWidth(100);
        ledgerTable.getColumnModel().getColumn(2).setCellRenderer(cellLeftRenderer);
        ledgerTable.getColumnModel().getColumn(3).setMinWidth(80);
        ledgerTable.getColumnModel().getColumn(3).setCellRenderer(cellRightRenderer);

        // CITE: <https://stackoverflow.com/q/39864139>
        trs = new TableRowSorter(ledgerTable.getModel());
        trs.setComparator(0, (x, y) -> ((Integer) x).compareTo((Integer) y));
        trs.setComparator(1, (x, y) -> ((String) x).compareTo((String) y));
        trs.setComparator(2, (x, y) -> ((String) x).compareTo((String) y));
        trs.setComparator(3, (x, y) -> {
            x = ((String) x).substring(3).replace(",", "");
            y = ((String) y).substring(3).replace(",", "");
            return ((Double) Double.parseDouble((String) x)).compareTo(Double.parseDouble((String) y));
        });
        ledgerTable.setRowSorter(trs);
        ledgerTable.setAutoCreateRowSorter(false);

        searchButton.addActionListener(event -> {
            trs.setRowFilter(new RowFilter() {
                // EFFECTS: returns true if row is to be displayed, false otherwise.
                //          each 'Entry' represents a row from the table.
                //          Format: QUERY [';' QUERY[...]]
                //          - QUERY: 'type:' ( 'in' ['flow'] | 'out' ['flow'] )
                //               | 'id:' <targetId>
                //               | 'ct:' <targetCreationDatetime>
                //               | 'desc:' <targetDescription>
                //               | 'amt:' <targetAmount>
                //          - <targetId>: \d+
                //          - <targetDatetime>: yyyy/MM/dd HH:mm:ss
                //          - <targetDescription>: (\w\s?)*
                //          - <targetAmount>: \d+(\.\d+)?
                // CITE: <https://youtu.be/U5Sh0KDLXSc?t=285>
                //       <https://stackoverflow.com/a/4662265/13992057>
                @Override
                public boolean include(Entry entry) {
                    String query = searchTextField.getText();
                    String targetType = Pattern.compile("type:(in(flow)?|out(flow)?)")
                            .matcher(query).results().map(res -> res.group(1)).findFirst().orElse(null);
                    String targetID = Pattern.compile("id:(\\d+)")
                            .matcher(query).results().map(res -> res.group(1)).findFirst().orElse(null);
                    String targetCreated = Pattern.compile("ct:(\\d\\d\\d\\d/\\d\\d/\\d\\d \\d\\d:\\d\\d:\\d\\d)")
                            .matcher(query).results().map(res -> res.group(1)).findFirst().orElse(null);
                    String targetDesc = Pattern.compile("desc:((?:\\w*\\s*)*)")
                            .matcher(query).results().map(res -> res.group(1)).findFirst().orElse(null);
                    String targetAmount = Pattern.compile("amt:(\\d+(?:\\.\\d+)?)")
                            .matcher(query).results().map(res -> res.group(1)).findFirst().orElse(null);
                    return (targetID == null || entry.getStringValue(0).equals(targetID))
                            && (targetCreated == null || entry.getStringValue(1).contains(targetCreated))
                            && (targetDesc == null || entry.getStringValue(2).contains(targetDesc))
                            && (targetAmount == null || entry.getStringValue(3).contains(targetAmount))
                            && (targetType == null
                            || (targetType.startsWith("in")
                            && entry.getStringValue(3).startsWith("+"))
                            || (targetType.startsWith("out")
                            && entry.getStringValue(3).startsWith("–")));
                }
            });
        });
    }
}
