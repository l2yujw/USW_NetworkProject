import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class webCrawlingRank implements Runnable{

    public static String main_poster;
    public static String main_title;
    public static String main_code;
    public static String[][] main_sum = new String[6][3];
    @Override
    public void run() {
        String url_main = "https://movie.naver.com/movie/sdb/rank/rmovie.naver";// 영화 랭킹

        Document doc_main = null;

        try {
            doc_main = Jsoup.connect(url_main).get();

            for (int i=1; i<7; i++) {
                Element el_main = doc_main.select(".list_ranking > tbody > tr").get(i);
                main_title = el_main.select(".tit3").text();// 영화명
                main_sum[i-1][0] = main_title;
                main_poster = el_main.select(".ac img").attr("src"); // 순위 사진
                main_sum[i-1][1] = main_poster;

                String code_main_sub = el_main.select(".tit3 a").attr("href");
                int code_start_main = code_main_sub.indexOf("code=");
                main_code = code_main_sub.substring(code_start_main+5,code_main_sub.length());// 선택한 영화 코드
                main_sum[i-1][2] = main_code;

                System.out.println(main_poster);
                System.out.println(main_title + ": " +main_code);
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
