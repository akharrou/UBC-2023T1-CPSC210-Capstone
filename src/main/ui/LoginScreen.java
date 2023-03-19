package ui;

import model.InvalidInputException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import static ui.Main.*;

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
    private JTextArea accountIdTextField;
    private JTextArea firstNameTextField;
    private JTextArea lastNameTextField;
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
    private JTextArea tncTextField;
    private JPanel footerSubPanel;

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

    private void createUIComponents() {
        registerButton.addActionListener(event -> {
            Main.register(
                    this.firstNameTextField.getText(),
                    this.lastNameTextField.getText(),
                    Double.parseDouble(this.tncTextField.getText())
            );
            this.setVisible(false);
            Main.accountScreen = new AccountScreen();
            dispose();
        });
        loginButton.addActionListener(event -> {
            Main.login(this.accountIdTextField.getText());
            int response = JOptionPane.showConfirmDialog(null, "Resume session?", "Title", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.NO_OPTION) {
                Main.account.reset();
            }
            this.setVisible(false);
            Main.accountScreen = new AccountScreen();
            this.dispose();
        });
    }
}
