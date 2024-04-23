package tests.server;

import server.threads.MovieCrawlingThread;
import tests.crawling.CrawlingThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 검색한 영화에 대한 정보를 받아 결과를 제공해줍니다.
 */
public class CrawlingServer {

    //Client에서 검색어를 받아서 넘김
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(5000);
            System.out.println("서버 가동됨");
            while (true) {
                System.out.println("Waiting client.Client...");
                socket = serverSocket.accept();

//                CrawlingThread crawlingThread = new CrawlingThread(socket);
//                crawlingThread.start();
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

}
