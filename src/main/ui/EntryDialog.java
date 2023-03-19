package ui;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

// Represents a dialog form for adding new financial entries.
public class EntryDialog extends JDialog {

    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField descTextField;
    private JTextField amtTextField;
    private JPanel headerPanel;
    private JPanel bodyPanel;
    private JPanel footerPanel;
    private JPanel actionPanel;
    private JPanel descSubPanel;
    private JPanel descPanel;
    private JPanel amtSubPanel;
    private JPanel amtPanel;

    // MODIFIES: this
    // EFFECTS: constructs and displays dialog form prompt. asks for financial entry information.
    //         if "Ok" is pressed then a new financial entry is created from the input information,
    //         and is added to the user's financial ledger. if "Cancel" is pressed, nothing happens.
    public EntryDialog() {
        this.setContentPane(contentPane);
        this.getRootPane().setDefaultButton(okButton);
        this.setResizable(false);
        this.createUIComponents();
        this.pack();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates and/or configures screen components.
    private void createUIComponents() {
        okButton.addActionListener(event -> {
            try {
                GuiApp.account.recordFinancialEntry(
                        Double.parseDouble(this.amtTextField.getText()), this.descTextField.getText()
                );
                GuiApp.accountScreen.updateScreen();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPane, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            this.dispose();
        });
        cancelButton.addActionListener(e -> dispose());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        contentPane.registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }
}
