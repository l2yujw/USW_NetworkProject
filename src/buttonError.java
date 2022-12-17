import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class buttonError extends JFrame {
    JButton btn = new JButton("시작");
    public buttonError () {
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(btn);
        setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatTestClient client = new chatTestClient("user1");
                client.start(); // 스레드의 start가 아님!!! 채팅창 gui 시작 start!
            }
        });
    }

    public static void main(String[] args) {
        new buttonError();
    }



}
