package Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUITest extends JFrame {
    public GUITest(String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Jframe이 정상적으로 종료되게 해줌.
        setBounds(0,0,500,500);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JButton btn = new JButton("Test Btn");
        JLabel label = new JLabel("Test Label");

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Click");
                label.setText("Click!!");
            }
        });

        JTextField txt = new JTextField(30);
//        panel.add(txt);
//        panel.add(btn);
//        panel.add(label);

        add(btn, BorderLayout.CENTER);
        add(label, BorderLayout.NORTH);
        add(txt, BorderLayout.EAST);
        txt.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println(txt.getText());
                label.setText(txt.getText());
            }
        });
        add(panel, BorderLayout.WEST);


        setVisible(true);



    }
}
