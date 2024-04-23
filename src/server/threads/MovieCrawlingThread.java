package server.threads;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import server.MovieCrawling;
import server.MovieRankCrawling;
import server.dto.MovieDto;
import server.dto.MovieRankDto;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
            if (search_title != null) {
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
            } else {
                String url_main = "https://movie.naver.com/movie/sdb/rank/rmovie.naver";// 영화 랭킹

                Document doc_main = null;

                MovieRankDto movieDto = new MovieRankDto();
                List<MovieRankDto> movie = new ArrayList<>();

                try {
                    doc_main = Jsoup.connect(url_main).get();

                    for (int i=1; i<7; i++) {
                        Element el_main = doc_main.select(".list_ranking > tbody > tr").get(i);
                        String main_title = el_main.select(".tit3").text();// 영화명
                        movieDto.setTitle(main_title);

                        String code_main_sub = el_main.select(".tit3 a").attr("href");
                        int code_start_main = code_main_sub.indexOf("code=");
                        String main_code = code_main_sub.substring(code_start_main+5,code_main_sub.length());// 선택한 영화 코드
                        movieDto.setPoster(main_code);

                        String url = "https://movie.naver.com/movie/bi/mi/basic.naver?code="+main_code;// 영화 정보

                        Document doc_movie = null;

                        doc_movie = Jsoup.connect(url).get();
                        Element el_movie = doc_movie.select(".mv_info_area").get(0);

                        String poster_url = el_movie.select(".poster img").attr("src"); // 영화 포스터 URL
                        poster_url = poster_url.substring(0,poster_url.lastIndexOf("?"));

                        movieDto.setUrl(poster_url);

                        movie.add(movieDto);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                oos.writeObject(movie);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
