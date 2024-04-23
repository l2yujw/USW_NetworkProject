package tests.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MovieCrawling {

    public static void main(String[] args) {
        String url = "https://www.moviechart.co.kr/rank/realtime/index/image";// 영화 랭킹
        Connection conn = Jsoup.connect(url);

        try {
            Document doc = conn.get();
            Elements movieList = doc.getElementsByClass("movieBox-item");



            for (Element element : movieList) {
                System.out.println(element.select("a img").attr("src"));
                System.out.println(element.select("a img").attr("alt"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
