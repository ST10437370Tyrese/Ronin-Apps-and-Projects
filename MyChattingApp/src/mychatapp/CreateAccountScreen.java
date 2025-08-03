package mychatapp;

import javax.swing.*;
import java.awt.*;

public class CreateAccountScreen extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private JTextField phoneField;
    private JTextArea messageArea;

    public CreateAccountScreen() {
        super("Chat App - Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLayout(new GridLayout(6, 2));

        JLabel userLabel = new JLabel("Username:");
        userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        passField = new JPasswordField();

        JLabel phoneLabel = new JLabel("Phone Number:(+27)");
        phoneField = new JTextField();

        JButton submitButton = new JButton("Create Account");
        messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);

        add(userLabel);
        add(userField);
        add(passLabel);
        add(passField);
        add(phoneLabel);
        add(phoneField);
        add(new JLabel(""));
        add(submitButton);
        add(new JLabel("Result:"));
        add(messageArea);

        submitButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String phone = phoneField.getText();

            String registrationResult = registerUser(username, password, phone);
            messageArea.setText(registrationResult);

            if (registrationResult.equals("Account created successfully!")) {
                JOptionPane.showMessageDialog(this, registrationResult + " You can now log in.");
                new LoginScreen().setVisible(true);
                dispose();
            }
        });
    }

    private String registerUser(String username, String password, String phone) {
        if (!checkUsername(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        AccountManagerScreen.createdUsername = username;
        AccountManagerScreen.createdPassword = password;
        return "Account created successfully!";
    }

    private boolean checkUsername(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    private boolean checkPasswordComplexity(String password) {
        return password != null &&
               password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }
}