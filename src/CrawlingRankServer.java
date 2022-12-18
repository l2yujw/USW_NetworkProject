import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CrawlingRankServer {
    public CrawlingRankServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(4000);
            System.out.println("서버 가동됨");
            Socket socket = serverSocket.accept();
            System.out.println("클라이언트 연결 접수됨...");
            System.out.println("[client] : " + socket.getInetAddress());

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ObjectOutputStream oos = new ObjectOutputStream(os);


            Thread thread = new Thread(new webCrawlingRank());
            thread.start();
            thread.join();

            MovieRankObj movieRankObj = new MovieRankObj();
            movieRankObj.setMain_title(webCrawlingRank.main_title);
            movieRankObj.setMain_poster(webCrawlingRank.main_poster);
            movieRankObj.setMain_code(webCrawlingRank.main_code);
            movieRankObj.setMain_sum(webCrawlingRank.main_sum);


            oos.writeObject(movieRankObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new CrawlingRankServer();
    }

}
