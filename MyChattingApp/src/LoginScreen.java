package mychatapp;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField loginUserField;
    private JPasswordField loginPassField;
    private JLabel loginMessageLabel;

    public LoginScreen() {
        super("Chat App - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        JLabel loginUserLabel = new JLabel("Username:");
        loginUserField = new JTextField();

        JLabel loginPassLabel = new JLabel("Password:");
        loginPassField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginMessageLabel = new JLabel("");

        add(loginUserLabel);
        add(loginUserField);
        add(loginPassLabel);
        add(loginPassField);
        add(new JLabel(""));
        add(loginButton);
        add(new JLabel(""));
        add(loginMessageLabel);

        loginButton.addActionListener(e -> {
            String enteredUsername = loginUserField.getText();
            String enteredPassword = new String(loginPassField.getPassword());

            boolean loginSuccess = AccountManagerScreen.loginUser(enteredUsername, enteredPassword);
            loginMessageLabel.setText(AccountManagerScreen.returnLoginStatus(loginSuccess, enteredUsername));
            
            if (loginSuccess) {
                new MainMenuScreen().setVisible(true);
                dispose();
            }
        });
    }
}