import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class javaGui extends JFrame {
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();


    public javaGui() {
        setSize(600, 700);
        setResizable(false);
        setLocationRelativeTo(null); // Frame 화면 가운데 위치
        setTitle("Network Project(Movie Review)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 메모리 제거
        setLayout(null);
        firstView();
        setVisible(true);
    }

    private void firstView() {

        // 검색창 Panel
        panel1.setBounds(0, 0, 600, 50);
        panel1.setBorder(new LineBorder(Color.black));
        JLabel searchLabel = new JLabel("검색창");
        JTextField searchField = new JTextField(40);
        JButton searchButton = new JButton("검색");
        JButton chat = new JButton("채팅");
        chat.setVisible(false);
        panel1.add(searchLabel);
        panel1.add(searchField);
        panel1.add(searchButton);
        panel1.add(chat);

        // 1~6순위 포스터 Panel
        panel2.setBounds(0, 50, 600, 500);
        panel2.setBorder(new LineBorder(Color.black));
        panel2.setLayout(null);
        JLabel[] poster = new JLabel[6];
        JLabel[] poster_head = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            poster[i] = new JLabel();
            poster_head[i] = new JLabel();
            if (i == 0) {
                poster[0].setBounds(45, 55, 160, 200);
                poster_head[0].setBounds(45, 40, 160, 15);
                poster_head[0].setText("1순위 : 영화이름"); // 영화이름 1차원 배열 설정 후 삽입
            }
            if (i == 1) {
                poster[1].setBounds(215, 55, 160, 200);
                poster_head[1].setBounds(215, 40, 160, 15);
                poster_head[1].setText("2순위 : 영화이름");
            }
            if (i == 2) {
                poster[2].setBounds(385, 55, 160, 200);
                poster_head[2].setBounds(385, 40, 160, 15);
                poster_head[2].setText("3순위 : 영화이름");
            }
            if (i == 3) {
                poster[3].setBounds(45, 280, 160, 200);
                poster_head[3].setBounds(45, 265, 160, 15);
                poster_head[3].setText("4순위 : 영화이름");
            }
            if (i == 4) {
                poster[4].setBounds(215, 280, 160, 200);
                poster_head[4].setBounds(215, 265, 160, 15);
                poster_head[4].setText("5순위 : 영화이름");
            }
            if (i == 5) {
                poster[5].setBounds(385, 280, 160, 200);
                poster_head[5].setBounds(385, 265, 160, 15);
                poster_head[5].setText("6순위 : 영화이름");
            }
            poster[i].setBorder(new LineBorder(Color.black, 1));
            putInPoster(poster[i]);
            panel2.add(poster[i]);
            panel2.add(poster_head[i]);
        }


        // 환영인사 Panel
        panel3.setBounds(0, 550, 600, 150);
        panel3.setBorder(new LineBorder(Color.black));
        panel3.setLayout(null);
        JLabel welcome = new JLabel();
        welcome.setText(Client.userName + "님 방문을 환영합니다!");
        welcome.setFont(new Font("", Font.BOLD, 25));
        welcome.setBackground(Color.yellow);
        welcome.setBounds(100, 30, 500, 40);
        panel3.add(welcome);

        add(panel1);
        add(panel2);
        add(panel3);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색어가 null 또는 해당 검색어가 존재하지 않으면, window 창 소환!
                // 검색어 null인 경우 / 검색한 영화명이 없는 경우 / 검색한 영화가 있는 경우
                if (searchField.getText().equals("")) {
                    System.out.println("검색어를 입력해주세요!");
                }
                else {
                    recycle(searchField.getText());
                }
            }
        });


    }

    private void recycle(String searchText) {
        // 검색창에 채팅 버튼 추가
        panel1.getComponent(3).setVisible(true);

        panel2.removeAll();
        JLabel poster = new JLabel();
        poster.setBounds(5, 3, 295, 270);
        poster.setBorder(new LineBorder(Color.black));
        putInPoster(poster, searchText);
        panel2.add(poster);

        JLabel grade = new JLabel();
        grade.setBounds(301, 3, 280, 90);
        grade.setBorder(new LineBorder(Color.black));
        grade.setLayout(new GridLayout(2,4));
        JLabel gradeAudience = new JLabel("관람객 ");
        gradeAudience.setHorizontalAlignment(JLabel.LEFT);
        JLabel audienceStar = new JLabel("8.69"); // 나중에 점수를 저장하는 변수가 필요함
        audienceStar.setFont(new Font("", Font.BOLD, 25));
        JLabel gradeCritic = new JLabel("평론가");
        gradeCritic.setHorizontalAlignment(JLabel.LEFT);
        JLabel criticStar = new JLabel("6.08");
        criticStar.setFont(new Font("", Font.BOLD, 25));
        JLabel gradeNetizen = new JLabel("네티즌");
        gradeNetizen.setHorizontalAlignment(JLabel.LEFT);
        JLabel netizenStar = new JLabel("9.14");
        netizenStar.setFont(new Font("", Font.BOLD, 25));
        JLabel gradeMy = new JLabel("내 평점");
        gradeMy.setHorizontalAlignment(JLabel.LEFT);
        JLabel myStar = new JLabel("0.0");
        myStar.setFont(new Font("", Font.BOLD, 25));
        grade.add(gradeAudience);
        grade.add(audienceStar);
        grade.add(gradeCritic);
        grade.add(criticStar);
        grade.add(gradeNetizen);
        grade.add(netizenStar);
        grade.add(gradeMy);
        grade.add(myStar);
        panel2.add(grade);

        JTextArea story = new JTextArea("줄거리");
        story.setLineWrap(true);
        story.setEditable(false);
        JScrollPane storyScroll = new JScrollPane(story);
        storyScroll.setBounds(301, 93, 280, 180);
        storyScroll.setBorder(new LineBorder(Color.black));
        panel2.add(storyScroll);


        String header[] = {"아이디", "내용", "평점", "날짜" };
        String contents[][] = {{"hwang", "정말 재미있어요정말 재미있어요정말 재미있어요정말 재미있어요정말 재미있어요", "8/10", "2022-10-11"}};
        JTable review = new JTable(contents, header);
        review.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        review.getColumn("아이디").setPreferredWidth(60);
        review.getColumn("내용").setPreferredWidth(400);
        review.getColumn("평점").setPreferredWidth(50);
        review.getColumn("날짜").setPreferredWidth(80);
        JScrollPane reviewScroll = new JScrollPane(review);
        reviewScroll.setBounds(5, 275, 576, 222);
        reviewScroll.setBorder(new LineBorder(Color.black));
        panel2.add(reviewScroll);
        
        
        // 내 리뷰글 + 평점 등록 칸
        panel3.removeAll();
        JTextArea reviewText = new JTextArea();
        reviewText.setBounds(5, 4, 400, 105);

        ImageIcon moiveIcon = new ImageIcon("recommendMovie/blank_star_icon.png");
        Image img = moiveIcon.getImage();
        Image changeimg = img.getScaledInstance(35, 55, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeimg);

        JTextField score = new JTextField();
        JLabel score10 = new JLabel("/ 10");
        score10.setBounds(510, 4, 75, 55);
        score10.setFont(new Font("", Font.BOLD, 30));
        score10.setHorizontalAlignment(JLabel.CENTER);
        score.setFont(new Font("", Font.BOLD, 30));
        score.setHorizontalAlignment(JTextField.CENTER);
        score.setBounds(410, 4, 100, 55);
        score.setBorder(new LineBorder(Color.black));
        panel3.add(score);
        panel3.add(score10);
        JButton registration = new JButton("등록");
        registration.setBounds(410, 61, 175, 50);
        panel3.add(registration);
        panel3.add(reviewText);

        setVisible(false);
        setVisible(true);

    }

    private void putInPoster(JLabel jLabel) {
        String moviename = "기생충";
        String url = "recommendMovie/" + moviename + ".png"; // 1순위 ~ 6순위 영화 포스터 받아올 때 파일명 정해져야 함. 매개변수에 i값 추가해서 moviename 1~6 지정해서 출력하게끔 변경한다.

        ImageIcon moiveIcon = new ImageIcon(url);
        Image img = moiveIcon.getImage();
        Image changeimg = img.getScaledInstance(160, 200, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeimg);
        jLabel.setIcon(changeIcon);
    }

    private void putInPoster(JLabel jLabel, String s) {
        String moviename = s;
        String url = "findMovie/" + moviename + ".png"; // 1순위 ~ 6순위 영화 포스터 받아올 때 파일명 정해져야 함. 매개변수에 i값 추가해서 moviename 1~6 지정해서 출력하게끔 변경한다.

        ImageIcon moiveIcon = new ImageIcon(url);
        Image img = moiveIcon.getImage();
        Image changeimg = img.getScaledInstance(295, 270, Image.SCALE_SMOOTH);
        ImageIcon changeIcon = new ImageIcon(changeimg);
        jLabel.setIcon(changeIcon);
    }

}