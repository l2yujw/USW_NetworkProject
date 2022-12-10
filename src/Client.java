import javax.swing.*;

public class Client {
    public static String userName;
    public static void checkID() {
        // User 명단 만들기 + 중복되는 유저명 생기면 뒤에 숫자 붙이기! // User 명단 DB관리?

        userName = JOptionPane.showInputDialog("사용할 id를 입력해주세요!");
        while (userName.trim().equals("")) {
            userName = JOptionPane.showInputDialog("아이디를 다시 입력해주세요!");
        }
        makeGUI();
    }

    public static void makeGUI() {
        // 전체 화면 창 설정
        new javaGui();

    }

    public static void main(String[] args) {
        checkID();
    }
}
