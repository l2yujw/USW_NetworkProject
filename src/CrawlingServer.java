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

            Thread thread = new Thread(new WebCrawling(search_title));
            thread.start();
            thread.join();

            Movie movie = new Movie();
            movie.setMovie_title(WebCrawling.movie_title);
            movie.setScore_adc(WebCrawling.score_adc);
            movie.setScore_spec(WebCrawling.score_spec);
            movie.setScore_ntz(WebCrawling.score_ntz);
            movie.setSummary(WebCrawling.summary);
            movie.setPoster(WebCrawling.poster);
            movie.setReview(WebCrawling.review);
            movie.setReview_score(WebCrawling.review_score);
            movie.setReview_reple(WebCrawling.review_reple);
            movie.setReview_user(WebCrawling.review_user);
            movie.setReview_date(WebCrawling.review_date);

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
