package ui;

import java.awt.Color;

import javax.swing.*;

public class Gui extends JFrame {

    private JPanel mainPanel;
    private JLabel ledgerLabel;
    private JList ledgerList;
    private DefaultListModel ledgerListModel = new DefaultListModel();
    private JLabel descLabel;
    private JTextField descTextField;
    private JLabel amountLabel;
    private JTextField amountTextField;
    private JButton addInflowButton;
    private JButton addOutflowButton;

    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;

    public Gui() {

        super("Financial Tracker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setBackground(new Color(0xFAFAFA));
        this.setResizable(false);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.pack();

        this.ledgerList.setModel(ledgerListModel);
        ledgerListModel.addElement("test");

        addInflowButton.addActionListener(e -> {
            System.out.println("Description: " + descTextField.getText());
            System.out.println("Amount: " + amountTextField.getText());
        });
    }
}
