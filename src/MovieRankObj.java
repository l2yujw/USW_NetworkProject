import java.io.Serializable;

public class MovieRankObj implements Serializable {
    private String main_title;
    private String main_poster;
    private String main_code;
    private String[][] main_sum = new String[6][3];

    public String getMain_title() {
        return main_title;
    }

    public void setMain_title(String main_title) {
        this.main_title = main_title;
    }

    public String getMain_poster() {
        return main_poster;
    }

    public void setMain_poster(String main_poster) {
        this.main_poster = main_poster;
    }

    public String getMain_code() {
        return main_code;
    }

    public void setMain_code(String main_code) {
        this.main_code = main_code;
    }

    public String[][] getMain_sum() {
        return main_sum;
    }

    public void setMain_sum(String[][] main_sum) {
        this.main_sum = main_sum;
    }
}
