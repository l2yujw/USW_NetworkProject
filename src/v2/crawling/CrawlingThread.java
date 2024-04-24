package v2.crawling;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import v2.dto.MovieDto;
import v2.dto.MovieSearchDto;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class CrawlingThread extends Thread {

    ObjectInputStream ois;
    ObjectOutputStream oos;
    OutputStream os;
    InputStream is;

    public CrawlingThread(Socket socket) {
        is = null;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();

            ois = new ObjectInputStream(is); //검색어
            oos = new ObjectOutputStream(os); //Dto 전송
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("실행");
        try {
            String search = (String) ois.readObject();
            System.out.println("search" + search);
            if (search != null) {
                //검색
                movieSearchCrawling(search);
            } else {
                //초기화면
                movieCrawling();
            }
            ois.close();
            is.close();
            os.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void movieCrawling() throws IOException {
        String url = "https://www.moviechart.co.kr/rank/realtime/index/image";// 영화 랭킹
        Connection conn = Jsoup.connect(url);

        Document doc = conn.get();
        Elements movieList = doc.getElementsByClass("movieBox-item");

        ArrayList<MovieDto> movieDtoList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            movieDtoList.add(
                    new MovieDto(
                            movieList.get(i).select("a img").attr("src"),
                            movieList.get(i).select("a img").attr("alt")
                    )
            );
        }
        oos.writeObject(movieDtoList);
        oos.close();
    }

    private void movieSearchCrawling(String search) throws IOException {
        String url = "https://www.moviechart.co.kr/search?search_keyword=" + search;// 영화 랭킹
        Connection connUrl = Jsoup.connect(url);

        Document doc = connUrl.get();
        Elements movieList = doc.getElementsByClass("movieBox-item");

        MovieSearchDto movieSearchDto = new MovieSearchDto();

        String title = movieList.select("a").attr("href");

        //검색
        String searchUrl = "https://www.moviechart.co.kr" + title;
        Connection connSearchUrl = Jsoup.connect(searchUrl);

        Document docSearch = connSearchUrl.get();
        String posterUrl = docSearch.getElementsByClass("poster").select("a").attr("href");
        System.out.println(posterUrl);

        Elements movieInf = doc.getElementsByClass("movieInerInfo-list depth").select("li dl");

        for (Element element : movieInf) {
            if (isEquals(element, "평점")) {
                movieSearchDto.setScore(element.select("dt").text());
            }
            if (isEquals(element, "장르")) {
                movieSearchDto.setGenre(element.select("dt").text());
            }
        }

        String summary = doc.getElementsByClass("text").text();
        movieSearchDto.setSummary(summary);

        oos.writeObject(movieSearchDto);
        oos.close();
    }

    private static boolean isEquals(Element element, String find) {
        return element.select("dd").text().equals(find);
    }
}
