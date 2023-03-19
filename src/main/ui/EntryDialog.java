package ui;

import model.FinancialAccount;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

import static java.lang.Thread.sleep;

public class EntryDialog extends JDialog {

    private JPanel contentPane;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField descTextField;
    private JTextField amtTextField;
    private JPanel descPanel;
    private JPanel headerPanel;
    private JPanel amtPanel;
    private JPanel bodyPanel;
    private JPanel footerPanel;
    private JPanel actionPanel;

    public EntryDialog() {
        okButton.addActionListener(event -> {
            Main.account.recordFinancialEntry(
                    Double.parseDouble(this.amtTextField.getText()), this.descTextField.getText()
            );
            Main.accountScreen.updateScreen();
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
        this.setContentPane(contentPane);
        this.getRootPane().setDefaultButton(okButton);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }
}
