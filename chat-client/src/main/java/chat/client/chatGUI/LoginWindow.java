package chat.client.chatGUI;

import chat.client.model.User;
import chat.client.model.UserCredentials;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;

public class LoginWindow extends JDialog {

    public LoginWindow(ChatWindow c) {
        super(c, "Logon", true);
        initialize(c);
    }

    private void initialize(ChatWindow c) {
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(c);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel serverIpLbl = new JLabel("Server");
        add(serverIpLbl);
        JTextField serverIP = new JTextField("1.1.1.1");
        add(serverIP);
        add(new JLabel("User:"));
        JTextField userId = new JTextField("Username");
        //set clear if focused
        add(userId);
        add(new JLabel("Password:"));
        JPasswordField userPassword = new JPasswordField("Password");
        add(userPassword);
        userId.addFocusListener(new FieldFocusListener("Username", userId));
        userPassword.addFocusListener(new FieldFocusListener("Password", userPassword));

        JButton proceedLogin = new JButton("Login");
        JButton cancelLogin = new JButton("Cancel");
        add(proceedLogin);
        add(cancelLogin);
        proceedLogin.addActionListener(e -> {

            String message = loginUser(c, serverIP.getText(), userId.getText(), userPassword.getPassword());
            while (c.currentUser != null && !c.currentUser.isOnline()) {
                String dialogHeader = "Login error!";
                int jOptionPaneTypeInfo = JOptionPane.ERROR_MESSAGE;
                if (message.isBlank() || message.equals("Go ahead!")) { //just to demonstrate functionality
                    dialogHeader = "Ok!";
                    jOptionPaneTypeInfo = JOptionPane.INFORMATION_MESSAGE;
                    c.currentUser.setOnline(true);
                    dispose();
                }
                JOptionPane.showMessageDialog(LoginWindow.this, message, dialogHeader, jOptionPaneTypeInfo);
            }
        });
        cancelLogin.addActionListener(e -> {
            dispose();
            c.currentUser = null;
            System.exit(0);
        });
        pack();
        setVisible(true);
    }

    private String loginUser( ChatWindow c, String serverId, String userId, char[] password) {
        //TODO create interface to work with password (hash) and server answer
        try {
            c.currentUser = new User("SomeStubUser", "Empty"
                    , Integer.parseInt(userId));
            UserCredentials userCredentials = new UserCredentials(Integer.parseInt(userId), Arrays.hashCode(password));
            return ServerAuthorizationStub.checkUserAuthority(userCredentials);
        } catch (NumberFormatException e) {
            return "Wrong input format. Try again!";
        }
    }


    private class FieldFocusListener implements FocusListener {
        private String defaultValue;
        private JTextField textField;

        FieldFocusListener(String defaultValue, JTextField textField) {
            this.defaultValue = defaultValue;
            this.textField = textField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(defaultValue)) {
                textField.setText("");
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(defaultValue);
            }
        }


    }
}
