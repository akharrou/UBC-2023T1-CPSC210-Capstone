package ui;

import static ui.GuiApp.MIN_FRAME_HEIGHT;
import static ui.GuiApp.MIN_FRAME_WIDTH;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

// Represents the GUI application's launch screen.
public class LaunchScreen extends JFrame {

    private static final int iconXCoord = (int) (MIN_FRAME_WIDTH * 0.5) - 100;
    private static final int iconYCoord = (int) (MIN_FRAME_HEIGHT * 0.125);
    private static final int buttonXCoord = (int) (MIN_FRAME_WIDTH * 0.5) - 75;
    private static final int buttonYCoord = iconYCoord + 300;

    JLabel launchLabel = new JLabel();
    JButton launchButton = new JButton();

    // EFFECTS: constructs and displays the application's launch screen.
    public LaunchScreen() {
        super("Fintrac");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(0xffFAFA));
        this.setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.setPreferredSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        this.setResizable(false);
        this.setLayout(null);
        this.createUIComponents();
        this.setVisible(true);
        this.pack();
    }

    // MODIFIES: this
    // EFFECTS: creates and/or configures screen components.
    private void createUIComponents() {

        launchLabel.setText("Fintrac");
        launchLabel.setFont(new Font("Hoefler Text", Font.PLAIN, 40));
        launchLabel.setForeground(new Color(0x616161));
        launchLabel.setIcon(new ImageIcon("assets/cash.gif"));
        launchLabel.setHorizontalTextPosition(JLabel.CENTER);
        launchLabel.setVerticalTextPosition(JLabel.TOP);
        launchLabel.setIconTextGap(15);
        launchLabel.setBounds(iconXCoord, iconYCoord, 300, 300);
        this.add(launchLabel);

        launchButton.setText("Launch");
        launchButton.setFont(new Font("Roboto", Font.BOLD, 16));
        launchButton.setForeground(new Color(0x616161));
        launchButton.setBackground(new Color(0xFAFAFA));
        launchButton.setBounds(buttonXCoord, buttonYCoord, 150, 50);
        launchButton.setFocusable(true);
        launchButton.addActionListener(e -> {
            // CITE: <https://www.youtube.com/watch?v=voykZF3Y5WY>
            this.setVisible(false);
            GuiApp.loginScreen = new LoginScreen();
            this.dispose();
        });
        this.add(launchButton);
    }
}
