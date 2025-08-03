package javaapplication1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JavaApplication1 {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chat App - Create Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(5, 2));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();

        JButton submitButton = new JButton("Create Account");
        JTextArea messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(new JLabel("")); // filler
        frame.add(submitButton);
        frame.add(new JLabel("Result:"));
        frame.add(messageArea);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                String phone = phoneField.getText();

                StringBuilder messages = new StringBuilder();

                // Username validation
                if (username.contains("_") && username.length() <= 5) {
                    messages.append("Username successfully captured.\n");
                } else {
                    messages.append("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.\n");
                }

                // Password validation
                if (isPasswordValid(password)) {
                    messages.append("Password successfully captured.\n");
                } else {
                    messages.append("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.\n");
                }

                // SA Cell number check
                if (phone.matches("0\\d{9}")) {
                    messages.append("SA cell number successfully captured.\n");
                } else {
                    messages.append("Invalid SA cell number. It must start with 0 and be 10 digits long.\n");
                }

                // International number check
                if (phone.matches("\\+\\d{1,10}")) {
                    messages.append("Cell phone number successfully added.");
                } else {
                    messages.append("Cell phone number incorrectly formatted or does not contain international code.");
                }

                messageArea.setText(messages.toString());
            }
        });

        frame.setVisible(true);
    }

    // Password complexity check
    private static boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}
