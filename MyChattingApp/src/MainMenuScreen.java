package mychatapp;

import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JFrame {
    public MainMenuScreen() {
        super("QuickChat - Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JTextArea welcomeArea = new JTextArea("Welcome to QuickChat.\n\nPlease select an option:");
        welcomeArea.setEditable(false);
        welcomeArea.setFont(new Font("Arial", Font.BOLD, 14));
        
        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        
        JButton sendMessagesButton = new JButton("1) Send Messages");
        JButton showMessagesButton = new JButton("2) Show recently sent messages");
        JButton messageOperationsButton = new JButton("3) Message Operations");
        JButton searchButton = new JButton("4) Search Messages");
        JButton reportsButton = new JButton("5) Generate Reports");
        JButton quitButton = new JButton("6) Quit");
        
        buttonPanel.add(sendMessagesButton);
        buttonPanel.add(showMessagesButton);
        buttonPanel.add(messageOperationsButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(reportsButton);
        buttonPanel.add(quitButton);
        
        add(welcomeArea, BorderLayout.NORTH);
        add(new JScrollPane(buttonPanel), BorderLayout.CENTER);
        
        sendMessagesButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "How many messages do you want to send?");
            try {
                MessageManagerScreen.maxMessages = Integer.parseInt(input);
                if (MessageManagerScreen.maxMessages > 0) {
                    new MessageInputScreen().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter a positive number");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number");
            }
        });
        
        showMessagesButton.addActionListener(e -> MessageManager.showRecentMessages());
        messageOperationsButton.addActionListener(e -> MessageManager.showMessageOperations());
        searchButton.addActionListener(e -> MessageManager.showSearchOptions());
        reportsButton.addActionListener(e -> MessageManager.generateReports());
        quitButton.addActionListener(e -> System.exit(0));
    }

    private static class MessageManager {

        private static void showRecentMessages() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private static void showMessageOperations() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private static void showSearchOptions() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private static void generateReports() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public MessageManager() {
        }
    }
}