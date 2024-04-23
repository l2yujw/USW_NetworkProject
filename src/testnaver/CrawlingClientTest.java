package test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class CrawlingClientTest {

    public static String search_title;
    public CrawlingClientTest() {
        try {
            Socket socket = new Socket("localhost", 5000);

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);

            Scanner scanner = new Scanner(System.in);
            search_title = scanner.next();
            oos.writeObject(search_title);

            Movie movie = (Movie) ois.readObject();
            String movie_title = movie.getMovie_title();
            String score_adc = movie.getScore_adc();
            String score_spec = movie.getScore_spec();
            String score_ntz = movie.getScore_ntz();
            String summary = movie.getSummary();
            String poster = movie.getPoster();
            String[][] review = movie.getReview();
            String review_score = movie.getReview_score();
            String review_reple = movie.getReview_reple();
            String review_user = movie.getReview_user();
            String review_date = movie.getReview_date();

            System.out.println(movie_title);
            System.out.println(score_adc + " " + score_spec + " " + score_ntz);
            System.out.println(summary);
            System.out.println(poster);
            System.out.println();
            for (int i=0; i<5; i++) {
                System.out.println(review[i][0]+ " " + review[i][1] + " " + review[i][2] + " " + review[i][3]);
            }


        } catch (IOException e) {
            System.out.println("오류");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("오류");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CrawlingClientTest();
    }
}
