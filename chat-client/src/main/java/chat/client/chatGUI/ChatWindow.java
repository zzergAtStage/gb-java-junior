package chat.client.chatGUI;

import chat.client.Client;
import chat.client.model.Chat;
import chat.client.model.ChatTransport;
import chat.client.model.Message;
import chat.client.model.User;
import chat.client.services.ChatFileTransport;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ChatWindow extends JFrame {
    public static final int WINDOW_HIGH = 555;
    public static final int WINDOW_WIDTH = 507;
    public static final int WINDOW_POSX = 200;
    public static final int WINDOW_POSY = 100;
    private static final int RETRY_DELAY_SECONDS = 15;
    JMenuBar menuBar;
    JMenu jMenu;
    JScrollPane paneUserList;
    JPanel windowContent;
    JList<String> listUser;
    Chat chatEntity;
    User currentUser;
    Socket server;
    ChatTransport transport;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy : HH:mm:ss");
    BufferedReader input;
    PrintWriter output;
    Read read;
    private JTextArea textBox = new JTextArea();
    private JMenuItem loginItem;
    private JMenuItem userLogOff;
    private JMenuItem exitProgram;
    //private Client client;

    public static void main(String[] args) {
        new ChatWindow();
    }

    public ChatWindow() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(WINDOW_POSX, WINDOW_POSY, WINDOW_WIDTH, WINDOW_HIGH);
        windowContent = new JPanel();
        GridBagLayout gb = new GridBagLayout();
        windowContent.setLayout(gb);
        initChat();

        listUser = getUserWindowList(gb);
        GridBagConstraints constr;

        listUser.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                System.out.println(listUser.getSelectedValue());
            }
        });

        //chat history box
        constr = getGridBagConstraints(3, 0, 3, 2,
                GridBagConstraints.FIRST_LINE_END, GridBagConstraints.BOTH);
        constr.weightx = 0.5;
        constr.weighty = 1.0;
        gb.setConstraints(textBox, constr);
        windowContent.add(textBox);
        textBox.setEditable(false);
        readChatHistory();

        //MessageField
        JTextField messageBox = new JTextField();
        constr = getGridBagConstraints(2, 2, 2, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        gb.setConstraints(messageBox, constr);
        windowContent.add(messageBox);
        messageBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessageFromBox(messageBox);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        //send button
        JButton sendBtn = new JButton("Send");
        sendBtn.addActionListener(e -> {
            sendMessageFromBox(messageBox);
        });
        constr = getGridBagConstraints(5, 2, 1, 1,
                GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE);
        gb.setConstraints(sendBtn, constr);
        windowContent.add(sendBtn);
        setContentPane(windowContent);
        //trying to fix panes scaling
        textBox.setLineWrap(true);
        textBox.setWrapStyleWord(true);
        addMenu();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                userLogOffMethod();
            }
        });
        setVisible(true);
        //client.listenForMessages(this);
    }

    private static GridBagConstraints getGridBagConstraints(int gridX, int gridY, int gridWidth, int gridHeight, int anchor, int fill) {
        GridBagConstraints constr = new GridBagConstraints();
        constr.insets = new Insets(10, 10, 10, 10);
        constr.gridx = gridX;
        constr.gridy = gridY;
        constr.gridwidth = gridWidth;
        constr.gridheight = gridHeight;
        constr.fill = fill;
        constr.weightx = (fill == GridBagConstraints.BOTH) ? 1.0 : 0.0;
        constr.weighty = (fill == GridBagConstraints.BOTH) ? 1.0 : 0.0;
        constr.anchor = anchor;
        return constr;
    }

    private void sendMessageFromBox(JTextField messageBox) {
        sendMessage(messageBox.getText());
        messageBox.setText("");
    }

    private void initChat() {
        chatEntity = new Chat();
        userLogin(this);
        // if (application.tunes("Use_File")) {
        //filelog
        transport = new ChatFileTransport();
        //} else { some DB init transport}
    }

    private JList<String> getUserWindowList(GridBagLayout gb) {
        GridBagConstraints constr = getGridBagConstraints(0, 0, 2, 2,
                GridBagConstraints.FIRST_LINE_START, GridBagConstraints.BOTH);
        JList<String> listUser = new JList<>();
        //listUser.setListData(new String[]{"User1", "User2", "User3", "User4", "User5"});
        //TODO: replace with retrieving list from server
        listUser.setListData(chatEntity.getActiveUsers().stream()
                .map(user -> user.getUserName() + " - " + user.getUserId())
                .toArray(String[]::new));

        paneUserList = new JScrollPane(listUser);
        constr.weighty = 1.0;
        constr.weightx = 0.5;
        gb.setConstraints(paneUserList, constr);
        windowContent.add(paneUserList);
        return listUser;
    }

    private void addMenu() {
        //This setting moves the menu into Apple Screen menu bar
        //System.setProperty("apple.laf.useScreenMenuBar", "true");
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        jMenu = new JMenu("File");
        loginItem = new JMenuItem("Login");
        loginItem.addActionListener(e -> {
            System.out.println("A login point");
            userLogin(this);
            chatEntity.addUserToChat(currentUser);
            System.out.println(chatEntity.getActiveUsers());
            repaint();
        });
        jMenu.add(loginItem);
        userLogOff = new JMenuItem("User log off");
        userLogOff.addActionListener(e -> userLogOffMethod());
        jMenu.add(userLogOff);
        jMenu.addSeparator();
        exitProgram = new JMenuItem("Exit");
        exitProgram.addActionListener(e -> {
            userLogOffMethod();
            System.exit(0);
        });
        jMenu.add(exitProgram);
        menuBar.add(jMenu);
    }

    private void userLogOffMethod() {
        currentUser = null;
        transport.saveMessage(chatEntity.getAllMessages());
        transport.closeResource();
        read.interrupt();
        output.close();
        System.exit(0);
    }

    private void userLogin(ChatWindow c) {
        LoginWindow loginWindow = new LoginWindow(c);
        //TODO replaceStub
        currentUser = new User("SomeNick", "Somevich", currentUser.getUserId());
        connect();
    }

    private void connect() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            server = new Socket(address, 4505);
            input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            output = new PrintWriter(server.getOutputStream(), true);


            //TODO: add client random identification


           // client = new Client(server, "Client + " + currentUser.getUserId());
            output.println(currentUser.getUserName()+"_"+currentUser.getUserId());
            read = new Read();
            read.start();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            handleConnectionError(e);
        }
    }

    private void handleConnectionError(Exception e) {
        showMessageDialog("Connection Error", e.getMessage() + " Please try again in 15 seconds.");
        try {
            Thread.sleep(RETRY_DELAY_SECONDS * 1000); // Convert seconds to milliseconds
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        // Retry the connection
        connect();
    }

    private void showMessageDialog(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void sendMessage(String messageBody) {
        if (messageBody.isBlank()) return;
        String time = LocalDateTime.now().format(formatter);
        Message message = new Message(String.valueOf(currentUser.getUserId()), "AtSome:"
                , time, messageBody);

        textBox.append("\n" + time + "@:" + message.getMessageBody() + "\n");
        chatEntity.addMessage(message);
        output.println(messageBody);
        //client.sendMessage(message);
        repaint();
    }

    public void readChatHistory() {
        List<Message> tmpChat = transport.readChatHistory();
        if (tmpChat == null) return;
        chatEntity.restoreChat(tmpChat);
        textBox.append(tmpChat.stream()
                .map(message -> message.getMessageSendTime() + "@:" + message.getMessageBody())
                .collect(Collectors.joining("\n"))
        );
    }

    public void putMessage(String message) {
        if (!message.isBlank()) {
            chatEntity.addMessage(new Message(null, null, new Date().toString(), message));
        } else {
            System.out.println("Message is empty?");
        }
    }


    class Read extends Thread {
        public void run() {
            String message;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    message = input.readLine();
                    if (message != null) {
                        System.out.println(message.getClass() + "_" + message);
                        if (message.charAt(0) == '[') {
                            message = message.substring(1, message.length() - 1);
                            ArrayList<String> ListUser = new ArrayList<>(
                                    Arrays.asList(message.split(", "))
                            );
                            listUser.setListData((String[]) ListUser.toArray());

                        } else {
                            putMessage(message);
                            textBox.append(message);
                        }
                    }
                } catch (IOException ex) {
                    System.err.println("Failed to parse incoming message");
                } catch (ClassCastException ex) {
                    System.err.println("Failed to receive clients list");
                }
            }
        }
    }
    private void appendToPane(JTextPane tp, String msg){
        HTMLDocument doc = (HTMLDocument)tp.getDocument();
        HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
        try {
            editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
            tp.setCaretPosition(doc.getLength());
        } catch(Exception e){
            e.printStackTrace();
        }
    }

}