package server;

import server.dto.MovieRankDto;

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

                CrawlingRankThread crawlingRankThread = new CrawlingRankThread(socket);
                crawlingRankThread.start();
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

    class CrawlingRankThread extends Thread{

        ObjectInputStream ois;
        ObjectOutputStream oos;
        OutputStream os;
        InputStream is;
        public CrawlingRankThread(Socket socket){
            is = null;
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
                ois = new ObjectInputStream(is);
                oos = new ObjectOutputStream(os);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            Thread thread = new Thread(new MovieRankCrawling());
            thread.start();
            try {
                thread.join();
                MovieRankDto movieRankObj = new MovieRankDto();
                movieRankObj.setMain_title(MovieRankCrawling.main_title);
                movieRankObj.setMain_poster(MovieRankCrawling.main_poster);
                movieRankObj.setMain_sum(MovieRankCrawling.main_sum);


                oos.writeObject(movieRankObj);//영화 랭킹 정보를 제공
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        new CrawlingRankServer();
    }

}
