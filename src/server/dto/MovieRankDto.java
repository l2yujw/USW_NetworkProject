package server.dto;

import java.io.Serializable;

/**
 * 영화랭킹 객체입니다.
 */
public class MovieRankDto implements Serializable {
    private String title;
    private String poster;

    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
