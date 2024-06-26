package v2.server;

import v2.crawling.CrawlingThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 검색한 영화에 대한 정보를 받아 결과를 제공해줍니다.
 */
public class CrawlingServer {

    /**
     *
     *  Client의 요청을 듣고만 있습니다.
     */
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("Server Check...");
            while (true) {
                System.out.println("Waiting v2.Client...");
                socket = serverSocket.accept();

                CrawlingThread crawlingThread = new CrawlingThread(socket);
                crawlingThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
                System.out.println("Sever Closed!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SeverSocket Communication Error!");
            }
        }
    }

}
