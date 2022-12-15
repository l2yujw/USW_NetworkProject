import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class chatTestClient extends JFrame{

    private String userID;

    // 초기화면 GUI
    
    // 채팅방 GUI
    private JLabel label = new JLabel();
    private JTextArea nowUsers = new JTextArea();
    private JScrollPane scroll1;
    private JTextArea chatting = new JTextArea();
    private JScrollPane scroll2;
    private JTextField fieldMsg = new JTextField();
    private JButton sendBtn = new JButton("보내기");

    public chatTestClient(String userID) {
        this.userID = userID;
        init();
        setVisible(true);
    }
    public void init() { // 새로운 창으로 띄워야 함 -> JFrame 생성 후 적용하기!
        setSize(400, 600); // 크기 지정
        setTitle("Movie Open Chatting Room(Network Project)"); // 제목
        setLayout(null); // 컴포넌트 위치 직접 지정
        setResizable(false); // 창 크기 고정
        setLocationRelativeTo(null); // Frame 화면 가운데 위치
//        setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 닫으면 종료

        label.setText("오픈 채팅방입니다!   " + userID + "님의 방문을 환영합니다!");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 400, 50);
        label.setBorder(new LineBorder(Color.black));

        nowUsers.setEditable(false);
        scroll1 = new JScrollPane(nowUsers);
        scroll1.setBounds(0, 50, 386, 100);

        chatting.setEditable(false);
        scroll2 = new JScrollPane(chatting);
        scroll2.setBounds(0, 150, 386, 350);

        fieldMsg.setBounds(0, 500, 306, 62);
        sendBtn.setBounds(306, 500, 80, 62);


        add(label);
        add(scroll1);
        add(scroll2);
        add(fieldMsg);
        add(sendBtn);
    } // Client GUI
    public void start() {
        Socket socket = null;
        BufferedReader in = null;
        ArrayList<String> users = new ArrayList<>();

        try {
            socket = new Socket("localhost", 6000);
            System.out.println("Connect to Server :  Success");
            //스레드 실행!
            Thread sendClientTread = new Thread(new SendClientThread(socket, userID));
            sendClientTread.start();

            System.out.println("[start()]" + userID);
            System.out.println("[start()]" + users);

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
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // 채팅 start!
    class SendClientThread implements Runnable {
        Socket socket = null;
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
                out.flush(); // Client의 userID Server에 전송
                System.out.println("[SendClientThread] : " + userID);
                while (true) {
                    sendBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(!fieldMsg.getText().equals("")) {
                                String outputMsg = fieldMsg.getText();
                                out.println(outputMsg);
                                out.flush();
                                fieldMsg.setText("");
                            }
                        }
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    } // Server로 보내는 Client 정보 (채팅용 스레드)
}
