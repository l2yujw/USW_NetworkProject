package v2.crawling.test;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import v2.dto.MovieSearchDto;

import java.io.IOException;

public class MovieSearchCrawling {

    public static void main(String[] args) {
        String url = "https://www.moviechart.co.kr/search?search_keyword=" + "파묘";// 영화 랭킹
        Connection connUrl = Jsoup.connect(url);

        try {
            Document doc = connUrl.get();
            Elements movieList = doc.getElementsByClass("movieBox-item");

            String title = movieList.select("a").attr("href");

            System.out.println(title);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String searchUrl = "https://www.moviechart.co.kr" + "/info/movieinfo/detail/20234675";
        Connection connSearchUrl = Jsoup.connect(searchUrl);

        MovieSearchDto movieSearchDto = new MovieSearchDto();

        try {
            Document doc = connSearchUrl.get();
            String posterUrl = doc.getElementsByClass("poster").select("a").attr("href");
            movieSearchDto.setPosterUrl(posterUrl);

            Elements movieInf = doc.getElementsByClass("movieInerInfo-list depth").select("li dl");

            for (Element element : movieInf) {
                if (isEquals(element, "평점")) {
                    movieSearchDto.setScore(element.select("dt").text());
                }
                if (isEquals(element, "장르")) {
                    movieSearchDto.setGenre(element.select("dt").text());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isEquals(Element element, String find) {
        return element.select("dd").text().equals(find);
    }
}
