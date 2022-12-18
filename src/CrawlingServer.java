import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 검색한 영화에 대한 정보를 받아 결과를 제공해줍니다.
 */
public class CrawlingServer {
    public CrawlingServer() {
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
            search_title = (String) ois.readObject();//Client가 검색한 영화

            Thread thread = new Thread(new webCrawling(search_title));
            thread.start();
            thread.join();

            MovieObj movie = new MovieObj();
            movie.setMovie_title(webCrawling.movie_title);
            movie.setScore_adc(webCrawling.score_adc);
            movie.setScore_spec(webCrawling.score_spec);
            movie.setScore_ntz(webCrawling.score_ntz);
            movie.setSummary(webCrawling.summary);
            movie.setPoster(webCrawling.poster);
            movie.setReview(webCrawling.review);
            movie.setReview_score(webCrawling.review_score);
            movie.setReview_reple(webCrawling.review_reple);
            movie.setReview_user(webCrawling.review_user);
            movie.setReview_date(webCrawling.review_date);

            oos.writeObject(movie);//검색한 영화에 대한 정보 제공


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
        new CrawlingServer();
    }

}
