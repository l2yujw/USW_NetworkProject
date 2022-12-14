import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        makeUserID();
    }

    public static void makeUserID() {
        String userID;
        boolean overlap;

        userID = JOptionPane.showInputDialog("사용할 아이디를 입력해주세요!");
        overlap = Server.checkUser(userID);
        while (userID.trim().equals("")) {
            userID = JOptionPane.showInputDialog("아이디를 입력해주세요!");
            overlap = Server.checkUser(userID);
        }
        while(overlap) {
            userID = JOptionPane.showInputDialog("이미 사용중인 아아디입니다. 다른 아이디를 입력해주세요!");
            overlap = Server.checkUser(userID);
        }

        new javaGui(userID);

    }

}
