package ui;

import model.InvalidInputException;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import static java.lang.Thread.sleep;
import static ui.GuiApp.*;

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
    private JPanel footerSubPanel;
    private JButton editTncButton;
    private JPanel editTncPanel;
    private JComboBox dropDownComboBox;
    private JPanel dropDownPanel;
    private JPanel addEntryButtonPanel;

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
        this.getContentPane().requestFocus();  // CITE: <https://stackoverflow.com/a/58008050>
    }

    public void updateScreen() {
        this.updateHeader();
        this.updateBody();
        this.updateFooter();
    }

    private void updateHeader() {
        this.accountNameLabel.setText(GuiApp.account.getFirstname() + " " + GuiApp.account.getLastname());
        this.accountIdLabel.setText(GuiApp.account.getID().toString().substring(0,7));
    }

    private void updateBody() {
        // CITE: <https://www.youtube.com/watch?v=teSBvFy9NH8>
        DefaultTableModel model = (DefaultTableModel) ledgerTable.getModel();
        model.setRowCount(0);
        GuiApp.account.getLedger().stream().forEach(elem -> {
            model.addRow(new Object[] {elem.getID(), elem.getCreated(), elem.getDescription(), elem.getAmountRepr()});
        });
        searchButtonDoClick();
    }

    private void updateFooter() {
        String pnc = String.format("Present Net Cashflow: %s $%,.2f",
                GuiApp.account.getPresentNetCashflow() >= 0 ? "" : "–",
                GuiApp.account.getPresentNetCashflow()
        );
        String tnc = String.format("Target Net Cashflow: %s $%,.2f",
                GuiApp.account.getTargetNetCashflow() >= 0 ? "" : "–",
                GuiApp.account.getTargetNetCashflow()
        );
        String fs = String.format("Financial Standing: %s",
                GuiApp.account.getPresentNetCashflow() > GuiApp.account.getTargetNetCashflow() ? "Good" : "Bad"
        );
        this.pncLabel.setText(pnc);
        this.tncLabel.setText(tnc);
        this.fsLabel.setText(fs);
    }

    private void createUIComponents() {
        setupTable();
        setupActions();
        setupWindow();
    }

    private void setupWindow() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Save session?", "Save", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    GuiApp.save();
                }
                dispose();
            }
        });
    }

    private void setupActions() {
        addEntryButton.addActionListener(e -> new EntryDialog());
        editTncButton.addActionListener(e -> {
            try {
                GuiApp.editGoal(JOptionPane.showInputDialog("New Target Cashflow:"));
                GuiApp.accountScreen.updateScreen();
            } catch (InvalidInputException exc) {
                // pass; already handled
            }
        });
        resetTableButton.addActionListener(event -> {
            GuiApp.account.getLedger().clear();
            GuiApp.accountScreen.updateScreen();
        });
        logoutButton.addActionListener(event -> {
            int response = JOptionPane.showConfirmDialog(null, "Save session?", "Title", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                GuiApp.save();
            }
            GuiApp.account = null;
            this.setVisible(false);
            GuiApp.loginScreen = new LoginScreen();
            this.dispose();
        });
        dropDownComboBox.addItemListener(e -> searchButtonDoClick());  // CITE: https://youtu.be/teSBvFy9NH8?t=1511
    }

    private void setupButtons() {

    }

    private void setupTable() {
        // CITE: <https://www.youtube.com/watch?v=3m1j3PiUeVI>
        ledgerTable.setModel(new DefaultTableModel(null, new String[] {"ID", "Created", "Description", "Amount"}));
        ledgerTable.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 12));
        ledgerTable.setRowHeight(20);
        this.setupTableRenderer();
        this.setupTableSorter();
        this.setupTableSearcher();
    }

    private void setupTableRenderer() {
        DefaultTableCellRenderer cellLeftRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellCenterRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer cellRightRenderer = new DefaultTableCellRenderer();
        cellLeftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        cellCenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

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
    }

    // CITE: <https://stackoverflow.com/q/39864139>
    private void setupTableSorter() {
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

    }

    // CITE: <https://stackoverflow.com/a/40516250>
    private void setupTableSearcher() {
        searchTextField.setText("Search\u200B");
        searchTextField.setForeground(Color.GRAY);
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchTextField.getText().equals("Search\u200B")) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setForeground(Color.GRAY);
                    searchTextField.setText("Search\u200B");
                }
            }
        });
        setupTableSearcherUpdate();
    }

    // CITE: <https://youtu.be/teSBvFy9NH8?t=1667>
    private void setupTableSearcherUpdate() {
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchButtonDoClick();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchButtonDoClick();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchButtonDoClick();
            }
        });
    }

    private void searchButtonDoClick() {
        trs.setRowFilter(new RowFilter() {
            @Override
            public boolean include(RowFilter.Entry entry) {
                String query = searchTextField.getText();
                Object menuItem = dropDownComboBox.getSelectedItem();
                return (query.equals("")
                            || query.equals("Search\u200B")
                            || entry.getStringValue(2).contains(query))
                        && (menuItem.equals("All")
                            || (menuItem.equals("Inflows")
                                && entry.getStringValue(3).startsWith("+"))
                            || (menuItem.equals("Outflows")
                                && entry.getStringValue(3).startsWith("–")));
            }
        });
    }
}
