package v2.client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient extends JFrame{

    /**
     * 필드값에 대한 설명입니다.
     */
    final private String userID;
    
    // 채팅방 GUI
    final private JLabel label = new JLabel();
    final private JTextArea nowUsers = new JTextArea();
    final private JTextArea chatting = new JTextArea();
    final private JTextField fieldMsg = new JTextField();
    final private JButton sendBtn = new JButton("보내기");

    public ChatClient(String userID) {
        this.userID = userID;
        init();
    }

    /**
     * 채팅방 Layout입니다.
     */
    public void init() { // 새로운 창으로 띄워야 함 -> JFrame 생성 후 적용하기!
        setSize(400, 600);
        setTitle("Movie Open Chatting Room(Network Project)");
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        label.setText("오픈 채팅방입니다!   " + userID + "님의 방문을 환영합니다!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 400, 50);
        label.setBorder(new LineBorder(Color.black));

        nowUsers.setEditable(false);
        JScrollPane scroll1 = new JScrollPane(nowUsers);
        scroll1.setBounds(0, 50, 386, 100);

        chatting.setEditable(false);
        JScrollPane scroll2 = new JScrollPane(chatting);
        scroll2.setBounds(0, 150, 386, 350);

        fieldMsg.setBounds(0, 500, 306, 62);
        sendBtn.setBounds(306, 500, 80, 62);

        add(label);
        add(scroll1);
        add(scroll2);
        add(fieldMsg);
        add(sendBtn);

        setVisible(true);
    }

    public void chatStart() {
        Socket socket = null;
        BufferedReader in;

        try {
            socket = new Socket("localhost", 6000);
            System.out.println("Connect to Server :  Success");
            //스레드 실행!
            Thread sendClientTread = new Thread(new SendClientThread(socket, userID));
            sendClientTread.start();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Server에서 받아온 문구 <입장, 퇴실> // 여기서 nowUsers Area에 현재 접속중인 사용자 띄우기.
            while (true) {
                String inputMsg = in.readLine();
                String username;

                if(inputMsg.equals("[" + userID + "]님이 나가셨습니다")) break;

                if(inputMsg.contains("사용자목록")) {
                    username = inputMsg.replace("사용자목록", "");
                    nowUsers.setText("현재 접속중인 사용자\n" + username);
                }
                else {
                    chatting.append(inputMsg + "\n");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "서버와 연결이 끊어졌습니다. 창을 종료합니다.", "Warning!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // 채팅 start!

    /**
     * Client에서 Server로 채팅 내용을 전달하고 Server로부터 여러 정보를 받아옵니다.
     */
    class SendClientThread implements Runnable {
        Socket socket;
        String userID;

        public SendClientThread(Socket socket, String userID) {
            this.socket = socket;
            this.userID = userID;
        }

        @Override
        public void run() {
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(userID);
                out.flush();
                while (true) {
                    sendBtn.addActionListener(e -> {
                        if(!fieldMsg.getText().equals("")) {
                            String outputMsg = fieldMsg.getText();
                            out.println(outputMsg);
                            out.flush();
                            fieldMsg.setText("");
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
