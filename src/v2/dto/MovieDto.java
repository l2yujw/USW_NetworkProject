package v2.dto;

import java.io.Serializable;

/**
 * 영화랭킹 객체입니다.
 */
public class MovieDto implements Serializable {
    private String title;

    private String posterUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
