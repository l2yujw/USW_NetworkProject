package server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * 영화 관련 필요한 정보를 가져옵니다.
 * page,REVIEWSIZE 변환시 MovieObj 같게 변환
 */
public class MovieCrawling implements Runnable{
    private static int REVIEW_SIZE = 10;

    public static String search_title;
    public static String movie_title;
    public static String score_adc;
    public static String score_spec;
    public static String score_ntz;
    public static String poster;

    public static int page = 2;
    public static String[][] review = new String[REVIEW_SIZE*page][4];
    public static String review_user;
    public static String review_reple;
    public static String review_score;
    public static String review_date;
    public static String summary;


    public MovieCrawling(String search_title) {
        this.search_title = search_title;
    }

    /**
     * 검색한 영화에대한 정보를 제공합니다.
     * movie_code_sub의 get()안에 숫자를 대입함으로서 검색시 나오는 결과들 중 n번째 결과를 받아올 수 있음
     */
    @Override
    public void run() {
//        search_title = "어벤져스";// 검색어
        String url_code = "https://movie.naver.com/movie/search/result.naver?section=movie&query="+search_title;

        Document doc_code = null;
        String movie_code;//영화 코드

        try {
            doc_code = Jsoup.connect(url_code).get();

            Element el_search = doc_code.select(".search_list_1").get(0);// 검색 결과들
            String movie_code_sub = String.valueOf(el_search.select(".result_thumb > a").get(0));// 검색 결과중 n번째 결과
            int code_start = movie_code_sub.indexOf("code=");
            int code_end = movie_code_sub.indexOf("\"><img");

            movie_code = movie_code_sub.substring(code_start+5,code_end);// 선택한 영화 코드

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * 영화 리뷰를 받아옵니다.
         */
        try {
            for (int k=0; k<page; k++) {
                String url_review = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.naver?code=+"+movie_code+"&type=after&isActualPointWriteExecute=false&isMileageSubscriptionAlready=false&isMileageSubscriptionReject=false#&page="+(page+1);// 영화 정보

                Document doc_review = null;
                doc_review = Jsoup.connect(url_review).get();

                for (int i=0; i<REVIEW_SIZE; i++) {
                    Element el_review = doc_review.select(".score_result > ul > li").get(i);// 리뷰 선택

                    Element reple_user = el_review.select(".score_reple > dl > dt > em").get(0);
                    review_user = reple_user.text();// 리뷰 작성자
                    review[i+k*REVIEW_SIZE][0] = review_user;

                    Element score_reple = el_review.select(".score_reple > p").get(0);
                    review_reple = score_reple.text();// 리뷰
                    review[i+k*REVIEW_SIZE][1] = review_reple;

                    Element star_score = el_review.select(".star_score").get(0);
                    review_score = star_score.text();// 리뷰 점수
                    review[i+k*REVIEW_SIZE][2] = review_score;

                    Element reple_date = el_review.select(".score_reple > dl > dt > em").get(1);
                    review_date = reple_date.text();// 리뷰 작성일
                    review[i+k*REVIEW_SIZE][3] = review_date;

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /**
         * 영화관련 정보를 받아옵니다.
         */
        String url = "https://movie.naver.com/movie/bi/mi/basic.naver?code="+movie_code;// 영화 정보

        Document doc_movie = null;

        //영화포스터 평점 줄거리
        try {
            doc_movie = Jsoup.connect(url).get();

            Element el_movie = doc_movie.select(".mv_info_area").get(0);
            movie_title = el_movie.select(".h_movie > a").first().text(); // 타이틀
            System.out.println("타이틀: " + movie_title);

            Element score_main = el_movie.select(".main_score").get(0);
            Elements score_ntz_all = score_main.select(".ntz_score").select(".star_score > em"); // 관람객 평점
            score_adc = score_ntz_all.text();
            score_adc = score_adc.replaceAll(" ","");

            Elements score_spec_all = score_main.select(".spc").select(".star_score > em"); // 평론가 평점
            score_spec = score_spec_all.text().replaceAll(" ", ""); // 평론가

            Elements score_adc_all = score_main.select("#pointNetizenPersentBasic > em"); // 네티즌 평점
            System.out.println(score_adc_all);
            score_ntz = score_adc_all.text().replaceAll(" ","");

            System.out.println("관람객:"+ score_adc + " 기자*평론가:" + score_spec + " 네티즌:" + score_ntz);

            if (doc_movie.select(".con_tx").size() > 0) {
                summary = doc_movie.select(".con_tx").first().text();
            } // 줄거리
            System.out.println(summary);

            System.out.println("줄거리: " + summary);

            poster = el_movie.select(".poster img").attr("src"); // 영화 포스터 URL
            poster = poster.substring(0,poster.lastIndexOf("?"));
            System.out.println("포스터: " + poster);

//            System.out.println();

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
