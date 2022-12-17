//package Test;
//
//import javax.sound.midi.MetaMessage;
//import java.io.*;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class CrawlingClientTest {
//
//    public static String search_title;
//    public CrawlingClientTest() {
//        try {
//            Socket socket = new Socket("localhost", 5000);
//
//            OutputStream os = socket.getOutputStream();
//            InputStream is = socket.getInputStream();
//
//            ObjectOutputStream oos = new ObjectOutputStream(os);
//            ObjectInputStream ois = new ObjectInputStream(is);
//
//            Scanner scanner = new Scanner(System.in);
//            search_title = scanner.next();
//
//            Thread thread = new Thread(new WebCrawlingTest("어벤져스"));
//            thread.start();
//            try {
//                thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Movie movie = new Movie();
//            movie.setMovie_title(WebCrawlingTest.movie_title);
//            movie.setScore_adc(WebCrawlingTest.score_adc);
//            movie.setScore_spec(WebCrawlingTest.score_spec);
//            movie.setScore_ntz(WebCrawlingTest.score_ntz);
//            movie.setPoster(WebCrawlingTest.poster);
//            movie.setReview_score(WebCrawlingTest.review_score);
//            movie.setReview_reple(WebCrawlingTest.review_reple);
//            movie.setReview_user(WebCrawlingTest.review_user);
//            movie.setReview_date(WebCrawlingTest.review_date);
//
//            oos.writeObject(movie);
//
//            String message;
//            message = (String) ois.readObject();
//            System.out.println(message);
//
//        } catch (IOException e) {
//            System.out.println("오류");
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            System.out.println("오류");
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        new CrawlingClientTest();
//    }
//}
