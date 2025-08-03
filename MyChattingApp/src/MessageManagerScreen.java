package mychatapp;

import java.awt.Dimension;
import javax.swing.*;
import org.json.simple.*;
import java.util.ArrayList;
import org.json.simple.parser.*;

public class MessageManagerScreen {
    public static ArrayList<MessageClass> sentMessages = new ArrayList<>();
    public static ArrayList<MessageClass> disregardedMessages = new ArrayList<>();
    public static ArrayList<MessageClass> storedMessages = new ArrayList<>();
    public static ArrayList<String> messageHashes = new ArrayList<>();
    public static ArrayList<String> messageIDs = new ArrayList<>();
    public static int messageCount = 0;
    public static int maxMessages = 0;

    public static void showRecentMessages() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Sent Messages ===\n");
        for (MessageClass Message : sentMessages) {
            sb.append(msg.printMessageDetails()).append("\n\n");
        }
        
        sb.append("\n=== Stored Messages ===\n");
        for (MessageClass Message : storedMessages) {
            sb.append(msg.printMessageDetails()).append("\n\n");
        }
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(null, scrollPane, "Recent Messages", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showMessageOperations() {
        String[] options = {
            "Display sender and recipients",
            "Find longest message",
            "Delete message by hash",
            "Back to main menu"
        };
        
        int choice = JOptionPane.showOptionDialog(null, 
            "Select an operation:", 
            "Message Operations", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            options, 
            options[0]);
        
        switch(choice) {
            case 0 -> displaySendersAndRecipients();
            case 1 -> findLongestMessage();
            case 2 -> deleteMessageByHash();
            default -> {
            }
        }
    }
    // ... (include all the other static methods from the original MyChatApp class)

    static void storeMessageToJSON(MessageClass message) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void displaySendersAndRecipients() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void findLongestMessage() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static void deleteMessageByHash() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}