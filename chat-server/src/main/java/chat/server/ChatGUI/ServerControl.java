package chat.server.ChatGUI;

import chat.server.model.ChatServer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ServerControl extends JFrame {
    public static final int WINDOW_HIGH = 555;
    public static final int WINDOW_WIDTH = 507;
    public static final int WINDOW_POSX = 800;
    public static final int WINDOW_POSY = 100;
    private final String SERVER_LABEL = "Is server working: ";
    private JLabel serverStatusLabel;
    private JTextArea serverMessages;

    public ServerControl() {
        ChatServer server = new ChatServer(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HIGH);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setResizable(false);
        JPanel paneAbove = new JPanel(new BorderLayout());
        JPanel panelBottom = new JPanel(new GridLayout(1, 2));
        serverMessages = new JTextArea();
        serverStatusLabel = new JLabel(SERVER_LABEL);
        JButton startServer = new JButton("Start server");
        startServer.addActionListener(e -> server.start());
        JButton stopServer = new JButton("Stop server");
        stopServer.addActionListener(e -> server.stop());

        paneAbove.add(serverStatusLabel, BorderLayout.NORTH);
        panelBottom.add(startServer, 0);
        panelBottom.add(stopServer, 1);
        paneAbove.add(panelBottom, BorderLayout.SOUTH);
        paneAbove.add(serverMessages, BorderLayout.CENTER);
        add(paneAbove);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ServerControl();
    }



    //announce observers about message event

    public void logStatus(String message) {
        if (serverMessages.getLineCount() > 20) serverMessages.setText("");
        serverMessages.append("\n" + message);
    }


    public void onMessageReceived(String message) {
        if (serverMessages.getLineCount() > 20) serverMessages.setText("");
        logStatus(message);
    }
}
