package v2.crawling.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import v2.dto.MovieDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieCrawling {

    public static void main(String[] args) {
        String url = "https://www.moviechart.co.kr/rank/realtime/index/image";// 영화 랭킹
        Connection conn = Jsoup.connect(url);

        List<MovieDto> movieDtos = new ArrayList<>();

        try {
            Document doc = conn.get();
            Elements movieList = doc.getElementsByClass("movieBox-item");
            System.out.println(movieList.get(0));
            MovieDto movieDto = new MovieDto("a", "b");

            for (int i = 0; i < 6; i++) {
                String title = movieList.get(i).select("a img").attr("src");
                String posterUrl = movieList.get(i).select("a img").attr("alt");

                movieDtos.add(movieDto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
