package v2.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 영화랭킹 객체입니다.
 */
public class MovieDto implements Serializable {
    private String title;

    private String posterUrl;

    public MovieDto(String title, String posterUrl) {
        this.title = title;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }
}
