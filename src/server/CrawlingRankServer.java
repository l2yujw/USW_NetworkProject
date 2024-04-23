package server;

import server.dto.MovieRankDto;
import server.threads.MovieRankCrawlingThread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 영화랭킹 정보를 제공합니다.
 */
public class CrawlingRankServer {
    public CrawlingRankServer(){
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(4000);
            System.out.println("서버 가동됨");
            while (true) {
                socket = serverSocket.accept();

                MovieRankCrawlingThread crawlingThread = new MovieRankCrawlingThread(socket);
                crawlingThread.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
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


    public static void main(String[] args) {
        new CrawlingRankServer();
    }

}
