//package Test;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//
//public class WebCrawlingTest implements Runnable{
//
//    public static String search_title;
//    public static String movie_title;
//    public static String score_adc;
//    public static String score_spec;
//    public static String score_ntz;
//    public static String poster;
//    public static String review_score;
//    public static String review_reple;
//    public static String review_user;
//    public static String review_date;
//
//    public WebCrawlingTest(String search_title) {
//        this.search_title = search_title;
//    }
//
//    public String getSearch_title() {
//        return search_title;
//    }
//
//    public void setSearch_title(String search_title) {
//        this.search_title = search_title;
//    }
//
//    public String getMovie_title() {
//        return movie_title;
//    }
//
//    public void setMovie_title(String movie_title) {
//        this.movie_title = movie_title;
//    }
//
//    public String getScore_adc() {
//        return score_adc;
//    }
//
//    public void setScore_adc(String score_adc) {
//        this.score_adc = score_adc;
//    }
//
//    public String getScore_spec() {
//        return score_spec;
//    }
//
//    public void setScore_spec(String score_spec) {
//        this.score_spec = score_spec;
//    }
//
//    public String getScore_ntz() {
//        return score_ntz;
//    }
//
//    public void setScore_ntz(String score_ntz) {
//        this.score_ntz = score_ntz;
//    }
//
//    public String getPoster() {
//        return poster;
//    }
//
//    public void setPoster(String poster) {
//        this.poster = poster;
//    }
//
//    public String getReview_score() {
//        return review_score;
//    }
//
//    public void setReview_score(String review_score) {
//        this.review_score = review_score;
//    }
//
//    public String getReview_reple() {
//        return review_reple;
//    }
//
//    public void setReview_reple(String review_reple) {
//        this.review_reple = review_reple;
//    }
//
//    public String getReview_user() {
//        return review_user;
//    }
//
//    public void setReview_user(String review_user) {
//        this.review_user = review_user;
//    }
//
//    public String getReview_date() {
//        return review_date;
//    }
//
//    public void setReview_date(String review_date) {
//        this.review_date = review_date;
//    }
//
//    @Override
//    public void run() {
////        search_title = "어벤져스";// 검색어
//        String url_code = "https://movie.naver.com/movie/search/result.naver?section=movie&query="+search_title;
//
//        Document doc_code = null;
//        String movie_code;
//
//        try {
//            doc_code = Jsoup.connect(url_code).get();
//
//            Element el_search = doc_code.select(".search_list_1").get(0);// 검색 결과들
//            String movie_code_sub = String.valueOf(el_search.select(".result_thumb > a").get(2));// 검색 결과중 n번째 결과
//            int code_start = movie_code_sub.indexOf("code=");
//            int code_end = movie_code_sub.indexOf("\"><img");
//
//            movie_code = movie_code_sub.substring(code_start+5,code_end);// 선택한 영화 코드
//
////            System.out.println(movie_code_sub);
////            System.out.println();
//
////            System.out.println(movie_code_sub);
////            System.out.println(code_start);
////            System.out.println(code_end);
////            System.out.println(movie_code);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        String url = "https://movie.naver.com/movie/bi/mi/basic.naver?code="+movie_code;// 영화 정보
//
//        Document doc_movie = null;
//
//        //영화포스터 평점 줄거리
//        try {
//            doc_movie = Jsoup.connect(url).get();
//
//            Element el_movie = doc_movie.select(".mv_info_area").get(0);
//            movie_title = el_movie.select(".h_movie > a").first().text(); // 타이틀
//            System.out.println("타이틀: " + movie_title);
//
//            Element score_main = el_movie.select(".main_score").get(0);
//            Elements score_all = score_main.select(".star_score"); // 평점
//
//            String score_adc_sub = score_all.get(0).text();
//            score_adc = score_adc_sub.substring(score_adc_sub.length()-4,score_adc_sub.length()); // 관람객
//            score_spec = score_all.get(1).text(); // 평론가
//            score_ntz = score_all.get(2).text(); // 네티즌
//
////            Element score_ntz = score_main.select()
//            System.out.println("관람객:"+ score_adc + " 기자*평론가:" + score_spec + " 네티즌:" + score_ntz);
//
//            String summary = "";
//            if (doc_movie.select(".con_tx").size() > 0) {
//                summary = doc_movie.select(".con_tx").first().text();
//            } // 줄거리
//
//            System.out.println("줄거리: " + summary);
//
//            poster = el_movie.select(".poster img").attr("src"); // 영화 포스터 URL
//            poster = poster.substring(0,poster.lastIndexOf("?"));
//            System.out.println("포스터: " + poster);
//            System.out.println();
//
//
//            for (int i=0; i<5; i++) {
//                Element el_review = doc_movie.select(".score_result > ul > li").get(i);// 리뷰 선택
//
//                Element star_score = el_review.select(".star_score").get(0);
//                review_score = star_score.text();// 리뷰 점수
//
//                Element score_reple = el_review.select(".score_reple > p").get(0);
//                review_reple = score_reple.text();// 리뷰
//
//                Element reple_user = el_review.select(".score_reple > dl > dt > em").get(0);
//                review_user = reple_user.text();// 리뷰 작성자
//
//                Element reple_date = el_review.select(".score_reple > dl > dt > em").get(1);
//                review_date = reple_date.text();// 리뷰 작성일
//
//                System.out.println(review_score + " : " + review_reple + " " + review_user + " " + review_date);
//            }
//
///*
//            Elements info = el.select(".info_spec > dd").first().select("span"); // 영화 정보 - 장르, 제작국, 러닝타임, 개봉날짜
//            				System.out.println("info : " + info);
//*/
//
////            String genre = null; // 장르
////            String country = null; // 제작국
////            String runtime = null; // 러닝타임
////            String date = null; // 개봉 날짜
////
////
////            for (int j=0; j<info.size(); j++) { // 장르, 제작국, 러닝타임, 개봉날짜
////                //					String a = info.get(j).text();
////                //					System.out.println(j + " : " + a);
////                genre = info.get(0).text();
////                country = info.get(1).text();
////                runtime = info.get(2).text();
////                date = info.get(3).text();
////            }
////
////            if (genre.indexOf(",") > 0) { // 장르 여러개일 경우 맨 첫번째 값만 저장
////                genre = genre.substring(0, genre.indexOf(","));
////            }
////
////            //System.out.println(date.indexOf("개"));
////            date = date.substring(0,11);
////            date = date.replace(".", "/");
////            date = date.replace(" ", "");
////
////            System.out.println("장르: " + genre);
////            System.out.println("제작국: " + country);
////            System.out.println("러닝타임: " + runtime);
////            System.out.println("개봉날짜: " + date);
//
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
