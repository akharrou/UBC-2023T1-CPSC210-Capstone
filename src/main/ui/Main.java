package ui;

import java.io.IOException;

// TUI or GUI application launcher class.
public class Main {
    // EFFECTS: launches the main application.
    public static void main(String[] args) throws IOException {
        if (args != null && args.length > 0) {
            if (args[0].equals("--tui") || args[0].equals("--console")) {
                new TuiApp();
                return;
            }
        }
        new GuiApp();
    }
}
