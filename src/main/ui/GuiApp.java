package ui;

import java.awt.*;

import javax.swing.*;

public class GuiApp extends JFrame {

    private boolean isRunning = true;

    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;

    public GuiApp() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.getContentPane().setBackground(new Color(0xFAFAFA));
        this.setIconImage(new ImageIcon("assets/logo.png").getImage());
        this.setTitle("Financial Tracker");
        this.setResizable(true);
        this.setVisible(true);

        try {
            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Terminating.");
    }

    private void run() {
        this.displayHomeScreen();
        while (this.isRunning) {
            this.sleep(1000);
        }
    }

    private void displayHomeScreen() {
//        JLabel labelLogo = new JLabel();
//        labelLogo.setText(" ");
//
//        labelLogo.setHorizontalTextPosition(JLabel.CENTER);
//        labelLogo.setVerticalTextPosition(JLabel.TOP);
//        this.add(labelLogo);

        JLabel labelAccountId = new JLabel();
        labelAccountId.setText("Account ID:");
        labelAccountId.setFont(new Font("Helvetica", Font.PLAIN, 20));
//        labelAccountId.setIcon(new ImageIcon("assets/logo.png"));
        labelAccountId.setHorizontalTextPosition(JLabel.LEFT);
        labelAccountId.setVerticalTextPosition(JLabel.CENTER);
        labelAccountId.setVerticalAlignment(JLabel.CENTER);
        labelAccountId.setHorizontalAlignment(JLabel.LEFT);
        labelAccountId.setForeground(new Color(0x212121));
        labelAccountId.setBackground(new Color(0xff0000));
        labelAccountId.setOpaque(true);
        this.add(labelAccountId);
        this.pack();
    }

    // REQUIRES: non-negative milliseconds
    // EFFECTS: sleeps for specified number of seconds.
    // CITATIONS:
    //  [1]: https://stackoverflow.com/a/24104427
    //  [2]: https://stackoverflow.com/a/24104332
    private void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) { /* ignore */ }
    }
}
