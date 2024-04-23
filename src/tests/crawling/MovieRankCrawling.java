package tests.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MovieRankCrawling {

    public static void main(String[] args) {
        String url = "https://www.moviechart.co.kr/search?search_keyword=" + "파묘";// 영화 랭킹
        Connection connUrl = Jsoup.connect(url);

        try {
            Document doc = connUrl.get();
            Elements movieList = doc.getElementsByClass("movieBox-item");

            for (Element element : movieList) {
                System.out.println(element.select("a").attr("href"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        String searchUrl = "https://www.moviechart.co.kr" + "/info/movieinfo/detail/20234675";
        Connection connSearchUrl = Jsoup.connect(searchUrl);

        try {
            Document doc = connSearchUrl.get();
            String posterUrl = doc.getElementsByClass("poster").select("a").attr("href");
            System.out.println(posterUrl);

            Elements movieInf = doc.getElementsByClass("movieInerInfo-list depth").select("li dl dt");
            System.out.println(movieInf.get(0));
            System.out.println(movieInf.get(1));
            System.out.println(movieInf.get(2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
