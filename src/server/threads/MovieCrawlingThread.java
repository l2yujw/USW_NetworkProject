package server.threads;

import server.MovieCrawling;
import server.dto.MovieDto;

import java.io.*;
import java.net.Socket;

public class MovieCrawlingThread extends Thread{
    ObjectInputStream ois;
    ObjectOutputStream oos;
    InputStream is;
    public MovieCrawlingThread(Socket socket){

        is = null;
        try {
            is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            ois = new ObjectInputStream(is);
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void run(){
        String search_title;
        try {
            search_title = (String) ois.readObject();//Client가 검색한 영화

            Thread thread = new Thread(new MovieCrawling(search_title));
            thread.start();
            thread.join();

            MovieDto movie = new MovieDto();
            movie.setMovie_title(MovieCrawling.movie_title);
            movie.setScore_adc(MovieCrawling.score_adc);
            movie.setScore_spec(MovieCrawling.score_spec);
            movie.setScore_ntz(MovieCrawling.score_ntz);
            movie.setSummary(MovieCrawling.summary);
            movie.setPoster(MovieCrawling.poster);
            movie.setReview(MovieCrawling.review);
            movie.setReview_score(MovieCrawling.review_score);
            movie.setReview_reple(MovieCrawling.review_reple);
            movie.setReview_user(MovieCrawling.review_user);
            movie.setReview_date(MovieCrawling.review_date);

            oos.writeObject(movie);//검색한 영화에 대한 정보 제공
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
