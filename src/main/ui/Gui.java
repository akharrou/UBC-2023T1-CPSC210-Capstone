package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    private JPanel mainPanel;
    private JTextField textField1;
    private JLabel label1;
    private JButton button1;
    private JLabel label2;

    private boolean isRunning = true;
    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;

    public Gui() {

        super("Financial Tracker");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLayout(null);
        this.setBackground(new Color(0xFAFAFA));
        this.setResizable(false);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.pack();

        button1.addActionListener(e -> {
            int tempFahr = (int) (Double.parseDouble((textField1.getText())) * 1.8 + 32);
            label2.setText(tempFahr + " Fahrenheit");
        });
    }
}
