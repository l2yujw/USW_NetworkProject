package v2.dto;

import java.io.Serializable;

/**
 * 검색한 영화에대한 객체입니다.
 * page, REVIEWSIZE 변환시 webCrawling 같게 변환
 */
public class MovieSearchDto implements Serializable {

    private String posterUrl;
    private String genre;
    private String score;

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
