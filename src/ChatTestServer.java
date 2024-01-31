import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 이곳은 Server 입니다.
 * 아직 DB구현은 하지 않은 상태입니다.
 */
public class ChatTestServer {
    /**
     * 필드값에 대한 설명입니다.
     * 아직 DB를 활용하지 않아서 UserList를 Client로부터 받아서 저장합니다.
     */
    static ArrayList<String> userList = new ArrayList<>(); // DB에 저장된 UserList ID 받아와서 저장

    public static void main(String[] args) {
        ChatTestServer server = new ChatTestServer();
        server.serverStart();
    }

    /**
     * 이 함수는 ID의 중복을 확인해주는 함수입니다. Client에서 id를 생성하면 Server에서 userList와 비교하여 중복여부를 확인합니다.
     * @param userID Client에서 생성한 ID를 의미합니다.
     * @return ID가 중복이면 True, 중복되지 않으면 False를 반환합니다.
     */
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

    /**
     * Server의 ServerSocket과 Socket을 생성합니다. Client에서 연결을 시도하면 Thread가 실행됩니다.
     * @see ReceiveClientThread
     */
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
        }
        finally {
            try {
                serverSocket.close();
                System.out.println("Sever Closed!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SeverSocket Communication Error!");
            }
        }

    }


    /**
     * 내부 클래스로 선언된 스레드입니다. 클라이언트로부터 받아온 정보를 Server에서 정리한 뒤 모든 Client에게 전송합니다.
     * 웹 크롤링 / 채팅 정보를 전달합니다.
     */
    class ReceiveClientThread extends Thread {

        static List<PrintWriter> list = Collections.synchronizedList(new ArrayList<PrintWriter>());

        Socket socket = null;
        BufferedReader in = null; //
        PrintWriter out = null; // 각 Client들에게 보낼 채팅 내용 및 수정사항

        /**
         * Client의 소켓으로부터 받아온 정보를 읽어오고 list에 userID를 추가합니다.
         * @param socket Client의 소켓 정보입니다.
         */
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

        /**
         * 스레드의 run()함수입니다.
         */
        @Override
        public void run() {
            String userID = "";

            try {
                userID = in.readLine();
                System.out.println("[" + userID + "와 새 연결 생성]");
                userList.add(userID);
                sendAll("[" + userID + "]님이 들어오셨습니다");
                sendAll("사용자목록" + userList);
                while (in != null) { // && userID != null
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

        /**
         * 각 Client에게 Server에서 Message를 보냅니다. (채팅내용)
         * @param s Client로 보낼 Message입니다.
         */
        private  void sendAll(String s) {
            for (PrintWriter out : list) {
                out.println(s);
                out.flush();
            }
        } // 현재 PrintWriter에 등록된 Client들에게 메시지 전달.
    } // Client들에게 userID와 chatting 내용을 받은 뒤 다시 Clent들에게 전송한다.
}

