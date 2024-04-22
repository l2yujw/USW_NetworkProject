package test;

import java.io.Serializable;

public class Movie implements Serializable {
    private static int REVIEW_SIZE = 5;
    private String movie_title;
    private String score_adc;
    private String score_spec;
    private String score_ntz;
    private String summary;
    private String poster;
    private String[][] review = new String[REVIEW_SIZE][4];
    private String review_score;
    private String review_reple;
    private String review_user;
    private String review_date;

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getScore_adc() {
        return score_adc;
    }

    public void setScore_adc(String score_adc) {
        this.score_adc = score_adc;
    }

    public String getScore_spec() {
        return score_spec;
    }

    public void setScore_spec(String score_spec) {
        this.score_spec = score_spec;
    }

    public String getScore_ntz() {
        return score_ntz;
    }

    public void setScore_ntz(String score_ntz) {
        this.score_ntz = score_ntz;
    }
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String[][] getReview() {
        return review;
    }

    public void setReview(String[][] review) {
        this.review = review;
    }

    public String getReview_score() {
        return review_score;
    }

    public void setReview_score(String review_score) {
        this.review_score = review_score;
    }

    public String getReview_reple() {
        return review_reple;
    }

    public void setReview_reple(String review_reple) {
        this.review_reple = review_reple;
    }

    public String getReview_user() {
        return review_user;
    }

    public void setReview_user(String review_user) {
        this.review_user = review_user;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

}
