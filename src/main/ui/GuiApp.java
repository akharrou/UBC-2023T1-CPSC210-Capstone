package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

        try {
            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Terminating.");
    }

    private void run() {
        this.displayAccountScreen();
        // this.displayHomeScreen();
        while (this.isRunning) {
            this.setVisible(true);
            this.sleep(100);
        }
    }

    private void displayAccountScreen() {

        this.setLayout(new BorderLayout(10,10));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel5a = new JPanel();
        JPanel panel5b = new JPanel();

        panel1.setBackground(Color.red);
        panel1.setPreferredSize(new Dimension(100, 100));
        panel2.setBackground(Color.green);
        panel2.setPreferredSize(new Dimension(100, 100));
        panel3.setBackground(Color.blue);
        panel3.setPreferredSize(new Dimension(100, 100));
        panel4.setBackground(Color.magenta);
        panel4.setPreferredSize(new Dimension(100, 100));

        panel5.setBackground(Color.cyan);
        panel5.setPreferredSize(new Dimension(100, 100));
        panel5.setLayout(new BorderLayout(10, 10));
        panel5a.setBackground(Color.lightGray);
        panel5a.setPreferredSize(new Dimension(767, 1000));
        panel5b.setBackground(Color.gray);
        panel5b.setPreferredSize(new Dimension(767, 1000));
        panel5.add(panel5a, BorderLayout.WEST);
        panel5.add(panel5b, BorderLayout.EAST);
        panel5b.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel5b.add(new JButton("All"));
        panel5b.add(new JButton("Inflows"));
        panel5b.add(new JButton("Outflows"));

        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.WEST);
        this.add(panel3, BorderLayout.EAST);
        this.add(panel4, BorderLayout.SOUTH);
        this.add(panel5, BorderLayout.CENTER);
    }

    private void displayHomeScreen() {

        JPanel rpanel = new JPanel();
        JPanel bpanel = new JPanel();
        JPanel gpanel = new JPanel();
        JLabel label = new JLabel();
        JButton button = new JButton();

        button.setEnabled(true);
        button.setBounds(100,250,200,50);
        button.setText("Get Started");
        button.setFont(new Font("Roboto", Font.BOLD, 20));
        button.setHorizontalAlignment(JButton.CENTER);
        button.setVerticalAlignment(JButton.CENTER);
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setForeground(new Color(0x212121));
        button.setBackground(new Color(0xFAFAFA));
        button.setFocusable(false);
        button.addActionListener(event -> {
            System.out.println("Button: [Get Started!] was pressed");
        });

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
        this.add(label, 0);
        this.add(button, 1);
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
