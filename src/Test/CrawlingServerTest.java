package Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CrawlingServerTest {
    public CrawlingServerTest() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("서버 가동됨");
            Socket socket = serverSocket.accept();
            System.out.println("클라이언트 연결 접수됨...");
            System.out.println("[client] : " + socket.getInetAddress());

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            ObjectOutputStream oos = new ObjectOutputStream(os);

            Movie movie = (Movie) ois.readObject();
            String movie_title = movie.getMovie_title();
            String score_adc = movie.getScore_adc();
            String score_spec = movie.getScore_spec();
            String score_ntz = movie.getScore_ntz();
            String poster = movie.getPoster();
            String review_score = movie.getReview_score();
            String review_reple = movie.getReview_reple();
            String review_user = movie.getReview_user();
            String review_date = movie.getReview_date();

            System.out.println(movie_title);
            System.out.println(score_adc);

            oos.writeObject(movie_title);

//            Member member = (Member) ois.readObject();
//            String id = member.getId();
//            String pwd = member.getPwd();
//            System.out.println("id : " + id + "\npwd : " + pwd);
//
            oos.writeObject( " 님 로그인 성공.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        new CrawlingServerTest();
    }
}
