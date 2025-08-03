package mychatapp;

import javax.swing.*;
import java.awt.*;

public class MessageInputScreen extends JFrame {
    private JTextField recipientField;
    private JTextArea messageArea;
    private JTextArea statusArea;

    public MessageInputScreen() {
        super("Send Messages");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel recipientLabel = new JLabel("Recipient Phone Number (+27):");
        recipientField = new JTextField();

        JLabel messageLabel = new JLabel("Message (max 250 chars):");
        messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane messageScroll = new JScrollPane(messageArea);

        JButton sendButton = new JButton("Send Message");
        JButton backButton = new JButton("Back to Menu");

        inputPanel.add(recipientLabel);
        inputPanel.add(recipientField);
        inputPanel.add(messageLabel);
        inputPanel.add(messageScroll);
        inputPanel.add(new JLabel(""));
        inputPanel.add(sendButton);
        inputPanel.add(new JLabel(""));
        inputPanel.add(backButton);

        statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(statusArea), BorderLayout.CENTER);

        sendButton.addActionListener(e -> {
            if (MessageManagerScreen.messageCount >= MessageManagerScreen.maxMessages) {
                JOptionPane.showMessageDialog(this,
                        "You've reached your maximum number of messages (" + MessageManagerScreen.maxMessages + ")");
                return;
            }

            String recipient = recipientField.getText();
            String messageText = messageArea.getText();

            if (messageText.length() > 250) {
                statusArea.append("Error: Please enter a message of less than 250 characters.\n");
                return;
            }

            if (!AccountManagerScreen.checkCellphoneNumber(recipient)) {
                statusArea.append("Error: Recipient number must be 10 digits starting with 0.\n");
                return;
            }

            MessageClass message = new MessageClass(recipient, messageText);

            String[] options = {"Send Message", "Disregard Message", "Store Message to send later"};
            int choice = JOptionPane.showOptionDialog(this,
                    "What would you like to do with this message?",
                    "Message Options",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == 0) { // Send
                MessageManagerScreen.sentMessages.add(message);
                MessageManagerScreen.messageCount++;
                MessageManagerScreen.messageHashes.add(message.createMessageHash());
                MessageManagerScreen.messageIDs.add(message.getMessageID());
                statusArea.append("Message sent successfully!\n");
                statusArea.append(message.printMessageDetails() + "\n\n");

                MessageManagerScreen.storeMessageToJSON(message);

                if (MessageManagerScreen.messageCount >= MessageManagerScreen.maxMessages) {
                    JOptionPane.showMessageDialog(this,
                            "You've sent all your messages (" + MessageManagerScreen.maxMessages + "). Total messages: " + MessageManagerScreen.messageCount);
                    new MainMenuScreen().setVisible(true);
                    dispose();
                }
            } else if (choice == 1) { // Disregard
                MessageManagerScreen.disregardedMessages.add(message);
                statusArea.append("Message disregarded.\n");
            } else if (choice == 2) { // Store
                MessageManagerScreen.storedMessages.add(message);
                MessageManagerScreen.storeMessageToJSON(message);
                statusArea.append("Message stored for later.\n");
            }

            recipientField.setText("");
            messageArea.setText("");
        });

        backButton.addActionListener(e -> {
            new MainMenuScreen().setVisible(true);
            dispose();
        });
    }
}
