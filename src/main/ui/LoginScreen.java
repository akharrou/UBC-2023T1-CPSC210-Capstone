package ui;

import model.InvalidInputException;

import javax.swing.*;
import java.awt.*;

import static ui.GuiApp.*;

// Represents the GUI application's login/registration screen.
public class LoginScreen extends JFrame {

    private JPanel rootPanel;
    private JPanel headerPanel;
    private JLabel fintrakLabel;
    private JButton registerButton;
    private JPanel rootSubPanel;
    private JPanel bodyPanel;
    private JPanel footerPanel;
    private JPanel registerPanel;
    private JPanel loginPanel;
    private JPanel loginSubPanel;
    private JTextField accountIdTextField;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JPanel accountIdPanel;
    private JPanel accountIdSubPanel;
    private JPanel registerFormPanel;
    private JPanel registerButtonPanel;
    private JPanel registerLabelPanel;
    private JLabel registerLabel;
    private JPanel firstNamePanel;
    private JPanel lastNamePanel;
    private JPanel registerSubPanel;
    private JButton loginButton;
    private JPanel tncPanel;
    private JTextField tncTextField;
    private JPanel footerSubPanel;

    // EFFECTS: constructs and displays the application's login/registration screen.
    public LoginScreen() {
        super("Fintrac");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0xFAFAFA));
        this.setContentPane(rootPanel);
        this.setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.setResizable(false);
        this.createUIComponents();
        this.pack();
        this.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates and/or configures screen components.
    private void createUIComponents() {
        registerButton.addActionListener(event -> {
            try {
                GuiApp.register(
                        this.firstNameTextField.getText(),
                        this.lastNameTextField.getText(),
                        this.tncTextField.getText()
                );
                this.setVisible(false);
                GuiApp.accountScreen = new AccountScreen();
                dispose();
            } catch (InvalidInputException e) {
                // pass; handled internally
            }
        });
        loginButton.addActionListener(event -> {
            try {
                GuiApp.login(this.accountIdTextField.getText());
                this.setVisible(false);
                GuiApp.accountScreen = new AccountScreen();
                this.dispose();
            } catch (InvalidInputException e) {
                // pass; handled internally
            }
        });
    }
}
