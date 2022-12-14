package Test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class chatTestServer {
    static ArrayList<String> userList = new ArrayList<>(); // DB에 저장된 UserList ID 받아와서 저장

    public static void main(String[] args) {
        chatTestServer server = new chatTestServer();
        server.serverStart();
    }

    public static boolean overlapCheck(String userID) {
        boolean overlap = false;
        for (String s : userList) {
            if (s.equals(userID)) {
                overlap = true;
                break;
            }
        }
        if (!overlap) {
            userList.add(userID);
        }
        return overlap;
    }

    public void serverStart() {
        ServerSocket serverSocket = null; // 서버의 ServerSocket
        Socket socket = null; // 클라이언트의 소켓 정보를 저장할 서버의 socket

        try {
            serverSocket = new ServerSocket(6000);

            while (true) {
                System.out.println("Waiting Client...");
                socket = serverSocket.accept();

                // Client가 접속할 때마다 새로운 Thread를 생성한다.
                ReceiveClientThread receiveClientThread = new ReceiveClientThread(socket);
                receiveClientThread.start();

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                System.out.println("Sever Closed!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SeverSocket Communication Error!");
            }
        }

    }

    // 내부 클래스 선언
    class ReceiveClientThread extends Thread {

        static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

        Socket socket = null;
        BufferedReader in = null; //
        PrintWriter out = null; // 각 Client들에게 보낼 채팅 내용 및 수정사항

        public ReceiveClientThread(Socket socket) {
            this.socket = socket;
            try {
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                list.add(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String userID = "";

            try {
                userID = in.readLine();
                System.out.println("[" + userID + "와 새 연결 생성]");
                userList.add(userID);
                sendAll("[" + userID + "]님이 들어오셨습니다");
                sendAll("사용자목록" + userList);
                while (in != null) {
                    String inputMsg = in.readLine();
                    sendAll(userID + ">>" + inputMsg);
                }

            } catch (IOException e) {
                System.out.println("[" + userID + "와 접속 끊김]");
            } finally {
                sendAll("[" + userID + "]님이 나가셨습니다");
                userList.remove(userID);
                sendAll("사용자목록" + userList);
                list.remove(out);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("[" + userID + "와 연결종료]");
        }

        private  void sendAll(String s) {
            for (PrintWriter out : list) {
                out.println(s);
                out.flush();
            }
        } // 현재 PrintWriter에 등록된 Client들에게 메시지 전달.
    } // Client들에게 userID와 chatting 내용을 받은 뒤 다시 Clent들에게 전송한다.
}

