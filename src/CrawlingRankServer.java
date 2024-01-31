import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 영화랭킹 정보를 제공합니다.
 */
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


            Thread thread = new Thread(new WebCrawlingRank());
            thread.start();
            thread.join();

            MovieRank movieRankObj = new MovieRank();
            movieRankObj.setMain_title(WebCrawlingRank.main_title);
            movieRankObj.setMain_poster(WebCrawlingRank.main_poster);
            movieRankObj.setMain_sum(WebCrawlingRank.main_sum);


            oos.writeObject(movieRankObj);//영화 랭킹 정보를 제공
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
