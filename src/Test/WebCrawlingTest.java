package Test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WebCrawlingTest {
    public static void main(String[] args) {
        String movie_title = "어벤져스";// 검색어
        String url_code = "https://movie.naver.com/movie/search/result.naver?section=movie&query="+movie_title;

        Document doc2 = null;
        String movie_code;

        try {
            doc2 = Jsoup.connect(url_code).get();

            Element el2 = doc2.select(".search_list_1").get(0);// 검색 결과들
            String movie_code_sub = String.valueOf(el2.select(".result_thumb > a").get(4));// 검색 결과중 n번째 결과
            int code_start = movie_code_sub.indexOf("code=");
            int code_end = movie_code_sub.indexOf("\"><img");

            movie_code = movie_code_sub.substring(code_start+5,code_end);// 선택한 영화 코드

//            System.out.println(movie_code_sub);
//            System.out.println();

//            System.out.println(movie_code_sub);
//            System.out.println(code_start);
//            System.out.println(code_end);
//            System.out.println(movie_code);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = "https://movie.naver.com/movie/bi/mi/basic.naver?code="+movie_code;

        Document doc = null;

        //영화포스터 평점 줄거리
        try {
            doc = Jsoup.connect(url).get();

            Element el = doc.select(".mv_info_area").get(0);
            String title = el.select(".h_movie > a").first().text(); // 타이틀
            System.out.println("타이틀: " + title);

            Element score_main = el.select(".main_score").get(0);
            Elements score_all = score_main.select(".star_score"); // 평점

            String score_adc_sub = score_all.get(0).text();
            String score_adc = score_adc_sub.substring(score_adc_sub.length()-4,score_adc_sub.length()); // 관람객
            String score_spec = score_all.get(1).text(); // 평론가
            String score_ntz = score_all.get(2).text(); // 네티즌

//            Element score_ntz = score_main.select()
            System.out.println("관람객 : "+ score_adc);
            System.out.println("기자*평론가 : " +score_spec);
            System.out.println("네티즌 : " + score_ntz);
            System.out.println();

            String summary = "";
            if (doc.select(".con_tx").size() > 0) {
                summary = doc.select(".con_tx").first().text();
            } // 줄거리

            System.out.println("줄거리: " + summary);

            String poster = el.select(".poster img").attr("src"); // 영화 포스터 URL
            poster = poster.substring(0,poster.lastIndexOf("?"));
            System.out.println("포스터: " + poster);

/*
            Elements info = el.select(".info_spec > dd").first().select("span"); // 영화 정보 - 장르, 제작국, 러닝타임, 개봉날짜
            				System.out.println("info : " + info);
*/

//            String genre = null; // 장르
//            String country = null; // 제작국
//            String runtime = null; // 러닝타임
//            String date = null; // 개봉 날짜
//
//
//            for (int j=0; j<info.size(); j++) { // 장르, 제작국, 러닝타임, 개봉날짜
//                //					String a = info.get(j).text();
//                //					System.out.println(j + " : " + a);
//                genre = info.get(0).text();
//                country = info.get(1).text();
//                runtime = info.get(2).text();
//                date = info.get(3).text();
//            }
//
//            if (genre.indexOf(",") > 0) { // 장르 여러개일 경우 맨 첫번째 값만 저장
//                genre = genre.substring(0, genre.indexOf(","));
//            }
//
//            //System.out.println(date.indexOf("개"));
//            date = date.substring(0,11);
//            date = date.replace(".", "/");
//            date = date.replace(" ", "");
//
//            System.out.println("장르: " + genre);
//            System.out.println("제작국: " + country);
//            System.out.println("러닝타임: " + runtime);
//            System.out.println("개봉날짜: " + date);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
