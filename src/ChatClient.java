import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ChatClient extends JFrame {

    public ChatClient(String userName) {
        setSize(400,600);
        setResizable(false);
        setLocationRelativeTo(null); // Frame 화면 가운데 위치
        setTitle("Open Chatting");
        setLayout(null);
        chatLayout(userName);
        setVisible(true);
    }

    private void chatLayout(String userName) {
        JLabel label1 = new JLabel();
        label1.setBounds(0,0,400,50);
        label1.setText("오픈 채팅방입니다!" + userName + "님의 방문을 환영합니다!!");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBorder(new LineBorder(Color.black));
        add(label1);

        JTextArea nowArea = new JTextArea("현재 접속자");
        nowArea.setEditable(false);
        nowArea.setText(nowArea.getText().toString() + "\n");
        JScrollPane nowUsers = new JScrollPane(nowArea);
        nowUsers.setBounds(0,50,400,100);
        nowUsers.setBorder(new LineBorder(Color.black));
        add(nowUsers);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatting = new JScrollPane(chatArea);
        chatting.setBounds(0,150,400,350);
        chatting.setBorder(new LineBorder(Color.black));
        add(chatting);


        JTextField txtMsg = new JTextField(10);
        txtMsg.setBounds(0,500,300,65);
        txtMsg.setBorder(new LineBorder(Color.black));
        add(txtMsg);

        JButton sendBtn = new JButton("보내기");
        sendBtn.setBounds(300, 500, 90, 62);
        add(sendBtn);
    }

    public static void main(String[] args) {

    }




}
