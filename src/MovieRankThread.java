//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//
//import java.io.IOException;
//
//public class MovieRankThread implements Runnable{
//    @Override
//    public void run() {
//        String url_main = "https://movie.naver.com/movie/sdb/rank/rmovie.naver";// 영화 랭킹
//
//        Document doc_main = null;
//
//        try {
//            doc_main = Jsoup.connect(url_main).get();
//
//            for (int i=1; i<11; i++) {
//                Element el_main = doc_main.select(".list_ranking > tbody > tr").get(i);
//                String poster_main = el_main.select(".ac img").attr("src"); // 순위 사진
//                String title_main = el_main.select(".tit3").text();// 영화명
//
//                String code_main_sub = el_main.select(".tit3 a").attr("href");
//                int code_start_main = code_main_sub.indexOf("code=");
//                String code_main = code_main_sub.substring(code_start_main+5,code_main_sub.length());// 선택한 영화 코드
//
//                System.out.println(poster_main);
//                System.out.println(title_main + ": " +code_main);
//                System.out.println();
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
