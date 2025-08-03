package mychatapp;

import javax.swing.SwingUtilities;

public class MyChattingApp {
    public static void main(String[] args) {
        // Start with the create account screen
        SwingUtilities.invokeLater(() -> {
            new CreateAccountScreen().setVisible(true);
        });
    }
}