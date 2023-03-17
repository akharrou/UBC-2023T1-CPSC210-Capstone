package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuiApp extends JFrame {

    private boolean isRunning = true;

    private static final int WINDOW_WIDTH = 1600;
    private static final int WINDOW_HEIGHT = 900;

    public GuiApp() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLayout(null);
        this.setBackground(new Color(0xFAFAFA));
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
            this.sleep(100);
        }
    }

    private void displayHomeScreen() {

        JPanel rpanel = new JPanel();
        JPanel bpanel = new JPanel();
        JPanel gpanel = new JPanel();
        JLabel label = new JLabel();

        rpanel.setBackground(Color.red);
        rpanel.setBounds(0,0,250,250);
        rpanel.setLayout(null);
        bpanel.setBackground(Color.blue);
        bpanel.setBounds(250,0,250,250);
        bpanel.setLayout(null);
        gpanel.setBackground(Color.green);
        gpanel.setBounds(0,250,500,250);
        gpanel.setLayout(null);

        label.setText("Financial Tracker");
        label.setForeground(new Color(0x212121));
        label.setFont(new Font("Helvetica", Font.PLAIN, 20));
        label.setIconTextGap(10);
        label.setIcon(new ImageIcon("assets/cash.gif"));
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.red, 3));
        label.setBackground(new Color(0xFAFAFA));
        label.setOpaque(true);
        label.setBounds(100, 0, 250, 250);

        this.add(rpanel);
        this.add(bpanel);
        this.add(gpanel);
        gpanel.add(label);
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
