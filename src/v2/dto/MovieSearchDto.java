package v2.dto;

import java.io.Serializable;

/**
 * 검색한 영화에대한 객체입니다.
 * page, REVIEWSIZE 변환시 webCrawling 같게 변환
 */
public class MovieSearchDto implements Serializable {

    private String genre;
    private String score;

}
