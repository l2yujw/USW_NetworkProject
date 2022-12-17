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

            String search_title;
            search_title = (String) ois.readObject();

            Thread thread = new Thread(new WebCrawlingTest(search_title));
            thread.start();
            thread.join();

            Movie movie = new Movie();
            movie.setMovie_title(WebCrawlingTest.movie_title);
            movie.setScore_adc(WebCrawlingTest.score_adc);
            movie.setScore_spec(WebCrawlingTest.score_spec);
            movie.setScore_ntz(WebCrawlingTest.score_ntz);
            movie.setSummary(WebCrawlingTest.summary);
            movie.setPoster(WebCrawlingTest.poster);
            movie.setReview(WebCrawlingTest.review);
            movie.setReview_score(WebCrawlingTest.review_score);
            movie.setReview_reple(WebCrawlingTest.review_reple);
            movie.setReview_user(WebCrawlingTest.review_user);
            movie.setReview_date(WebCrawlingTest.review_date);

            oos.writeObject(movie);


//            Member member = (Member) ois.readObject();
//            String id = member.getId();
//            String pwd = member.getPwd();
//            System.out.println("id : " + id + "\npwd : " + pwd);
//
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        new CrawlingServerTest();
    }
}
