package chat.client.chatGUI;

import chat.client.model.User;
import chat.client.model.UserCredentials;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginWindow extends JDialog{

    public LoginWindow(ChatWindow c){
        super(c,"Logon", true);
        initialize(c);
    }

    private void initialize(ChatWindow c) {
        setLayout(new GridLayout(4,2));
        setLocationRelativeTo(c);
        setModal(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel serverIpLbl = new JLabel("Server");
        add(serverIpLbl);
        JTextField serverIP = new JTextField("1.1.1.1");
        add(serverIP);
        add(new JLabel("User:"));
        JTextField userId = new JTextField("User id");
        //set clear if focused
        add(userId);
        add(new JLabel("Password:"));
        JPasswordField userPassword = new JPasswordField("User password");
        add(userPassword);
        JButton proceedLogin = new JButton("Login");
        JButton cancelLogin = new JButton("Cancel");
        add(proceedLogin);
        add(cancelLogin);
        proceedLogin.addActionListener( e -> {
            String message = loginUser(serverIP.getText(), userId.getText(),userPassword.getPassword());
            String dialogHeader = "Login error!";
            if (message.isBlank() || message.equals("Go ahead!")) { //just to demonstrate functionality
                dialogHeader = "Ok!";
                c.currentUser = new User("SomeStubUser","Empty"
                        ,Integer.parseInt(userId.getText()));
                dispose();
            }
            JOptionPane.showMessageDialog(LoginWindow.this, message, dialogHeader, JOptionPane.ERROR_MESSAGE);

        });
        cancelLogin.addActionListener( e -> {
            dispose();
            c.currentUser = null;
        });
        pack();
        setVisible(true);
    }

    private String loginUser(String serverId, String userId, char[] password) {
        //TODO create interface to work with password (hash) and server answer
        try{
            UserCredentials user = new UserCredentials(Integer.parseInt(userId), Arrays.hashCode(password));
            return ServerAuthorizationStub.checkUserAuthority(user);
        } catch (NumberFormatException e) {
            return "Wrong input format. Try again!";
        }
    }


}
