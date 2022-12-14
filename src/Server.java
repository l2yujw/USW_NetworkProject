import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
    }
    // 클라이언트에서 아이디 수신한다. -> 아이디의 중복여부를 확인한다. -> 중복이면 아이디 새로 설정하라고 하고, 아니면 아이디 추가한다.
    public static boolean checkUser(String userID) {
        ArrayList<String> userList = new ArrayList<String>(Arrays.asList("user1", "user2")); // DB 사용!
        boolean overlap = false;
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).equals(userID)) {
                overlap = true;
            }
        }
        return overlap;
    }

    // 웹 크롤링을 통한 1순위 ~ 6순위 영화 포스터 및 영화이름 가져와서 javaGui에 전송.
    //[2]
    // Client에서 입력한 영화이름을 받아온다.
    // 해당 영화 이름으로 웹 크롤링을 한다.
    // 웹 크롤링한 정보를 Client로 보낸다.
    //
    //[3]
    // Client로부터 영화 리뷰 평을 받아온다.
    // 서버의 리뷰 저장 파일에 받아온 리뷰 평을 저장한다.
    // 다시 Client에 리뷰평을 전달한다.
}



