import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonError extends JFrame {
    JButton btn = new JButton("시작");
    public ButtonError() {
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(btn);
        setVisible(true);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChatTestClient client = new ChatTestClient("user1");
                client.start(); // 스레드의 start가 아님!!! 채팅창 gui 시작 start!
            }
        });
    }

    public static void main(String[] args) {
        new ButtonError();
    }



}
