import Test.CrawlingClientTest;
import Test.Movie;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends JFrame {
    /**
     * 크롤링한 값을 저장할 변수입니다.
     */
    public static String search_title;
    public static String movie_title;
    public static String score_adc;
    public static String score_spec;
    public static String score_ntz;
    public static String summary;
    public static String poster_site;
    public static String[][] review_sum;
    public static String review_score;
    public static String review_reple;
    public static String review_user;
    public static String review_date;
    /**
     * 필드값에 대한 설명입니다. 대부분 이름의 뜻과 같습니다.
     */
    private static String userID;
    private JPanel search;
    private JLabel label_p1;
    private JTextField text_p1;
    private JButton btn1_p1;
    private JPanel poster;
    private JLabel[] label1_p2;
    private JLabel[] label2_p2;
    private JPanel welcome;
    private JLabel label_p3;
    // recycle
    private final JLabel movieposter = new JLabel();
    private final JLabel grade = new JLabel();
//    private final JLabel gradeAudience = new JLabel("관람객 ");
//    private final JLabel audienceStar = new JLabel("8.69");
//    private final JLabel gradeCritic = new JLabel("평론가");
//    private final JLabel criticStar = new JLabel("6.08");
//    private final JLabel gradeNetizen = new JLabel("네티즌");
//    private final JLabel netizenStar = new JLabel("9.14");
//    private final JLabel gradeMy = new JLabel("내 평점");
//    private final JLabel myStar = new JLabel("0.0");
//    private final JTextArea story = new JTextArea("줄거리");
//    String header[] = {"아이디", "내용", "평점", "날짜"};
//    String contents[][] = {{"hwang", "정말 재미있어요정말 재미있어요정말 재미있어요정말 재미있어요정말 재미있어요", "8/10", "2022-10-11"}};
//    private JTable review = new JTable(contents,header);
//    private final JScrollPane storyScroll = new JScrollPane(story);
//    private final JScrollPane reviewScroll = new JScrollPane(review);
    private final JTextField score = new JTextField();
    private final JLabel score10 = new JLabel("/ 10");
    private final JButton registration = new JButton("등록");
    // panel3
    private final JTextArea reviewText = new JTextArea();

    public static void main(String[] args) {
        checkID();
        new Client();
        chatTestClient client = new chatTestClient(userID);
        client.start(); // Client의 Socket를 만드는 start 함수임, Thread의 start함수가 아님!
    }

    public void CrawlingClient() {
        try {
            Socket socket = new Socket("localhost", 5000);

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);

            oos.writeObject(search_title);

            MovieObj movieObj = (MovieObj) ois.readObject();
            movie_title = movieObj.getMovie_title();
            score_adc = movieObj.getScore_adc();
            score_spec = movieObj.getScore_spec();
            score_ntz = movieObj.getScore_ntz();
            summary = movieObj.getSummary();
            poster_site = movieObj.getPoster();
            review_sum = movieObj.getReview();
            review_score = movieObj.getReview_score();
            review_reple = movieObj.getReview_reple();
            review_user = movieObj.getReview_user();
            review_date = movieObj.getReview_date();

            System.out.println(movie_title);
            System.out.println(score_adc + " " + score_spec + " " + score_ntz);
            System.out.println(summary);
            System.out.println(poster_site);
            System.out.println();
            for (int i=0; i<5; i++) {
                System.out.println(review_sum[i][0]+ " " + review_sum[i][1] + " " + review_sum[i][2] + " " + review_sum[i][3]);
            }


        } catch (IOException e) {
            System.out.println("오류");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("오류");
            e.printStackTrace();
        }
    }

    /**
     * JFrame 생성합니다.
     */
    public Client() {
        setSize(600, 700);
        setResizable(false);
        setLocationRelativeTo(null); // Frame 화면 가운데 위치
        setTitle("Network Project(Movie Review)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 메모리 제거
        setLayout(null);
        frameView();
        setVisible(true);
    }

    /**
     * 아이디를 생성할 때 중복 여부를 확인합니다. 중복 확인은 Server에서 합니다.
     */
    public static void checkID() {
        boolean overlap;

        userID = JOptionPane.showInputDialog("사용할 아이디를 입력해주세요!");
        if (userID.trim().equals("")) {
            while (userID.trim().equals("")) {
                userID = JOptionPane.showInputDialog("아이디를 입력해주세요!");
            }
        }
        overlap = chatTestServer.overlapCheck(userID);
        while (overlap) {
            userID = JOptionPane.showInputDialog("이미 사용중인 아아디입니다. 다른 아이디를 입력해주세요!");
            overlap = chatTestServer.overlapCheck(userID);
        }
    }

    /**
     * 초기 화면과 검색창에 영화 제목을 넣어 검색했을 때의 Layout을 구성합니다.
     */
    private void frameView() {
        // 검색창 panel1
        search = new JPanel();
        poster = new JPanel();
        poster.setLayout(null);
        welcome = new JPanel();


        label_p1 = new JLabel("검색창");
        text_p1 = new JTextField(38);
        btn1_p1 = new JButton("검색");
        search.add(label_p1);
        search.add(text_p1);
        search.add(btn1_p1);
        search.setBounds(0,0,600,50);
        search.setBorder(new LineBorder(Color.black));

        // 1 ~ 6순위 포스터 panel2
        label1_p2 = new JLabel[6]; // 포스터 사진
        label2_p2 = new JLabel[6]; // [순위]영화 제목
        for(int i = 0; i<6; i++) { // 영화 포스터 삽입 + 영화 제목 삽입
            label1_p2[i] = new JLabel();
            label2_p2[i] = new JLabel();
            if (i == 0) {
                label1_p2[0].setBounds(45, 55, 160, 200);
                label2_p2[0].setBounds(45, 40, 160, 15);
                label2_p2[0].setText("1순위 : 영화이름"); // 영화이름 1차원 배열 설정 후 삽입
            }
            if (i == 1) {
                label1_p2[1].setBounds(215, 55, 160, 200);
                label2_p2[1].setBounds(215, 40, 160, 15);
                label2_p2[1].setText("2순위 : 영화이름");
            }
            if (i == 2) {
                label1_p2[2].setBounds(385, 55, 160, 200);
                label2_p2[2].setBounds(385, 40, 160, 15);
                label2_p2[2].setText("3순위 : 영화이름");
            }
            if (i == 3) {
                label1_p2[3].setBounds(45, 280, 160, 200);
                label2_p2[3].setBounds(45, 265, 160, 15);
                label2_p2[3].setText("4순위 : 영화이름");
            }
            if (i == 4) {
                label1_p2[4].setBounds(215, 280, 160, 200);
                label2_p2[4].setBounds(215, 265, 160, 15);
                label2_p2[4].setText("5순위 : 영화이름");
            }
            if (i == 5) {
                label1_p2[5].setBounds(385, 280, 160, 200);
                label2_p2[5].setBounds(385, 265, 160, 15);
                label2_p2[5].setText("6순위 : 영화이름");
            }
            label1_p2[i].setBorder(new LineBorder(Color.black));
            // 포스터 넣기
            poster.add(label1_p2[i]);
            poster.add(label2_p2[i]);

        }

        poster.setBounds(0, 50, 600, 500);
        poster.setBorder(new LineBorder(Color.black));


        // 환영문구 panel3
        label_p3 = new JLabel(userID + "님 방문을 환영합니다.");
        label_p3.setFont(new Font("", Font.BOLD, 30));
        label_p3.setHorizontalAlignment(JLabel.CENTER);
        welcome.add(label_p3);
        welcome.setBounds(0, 550, 600, 150);
        welcome.setBorder(new LineBorder(Color.black));

        add(search);
        add(poster);
        add(welcome);

        btn1_p1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text_p1.getText().equals("")) {
                    JOptionPane.showMessageDialog(getContentPane(), "검색어를 입력해주세요!");
                }
                else {
                    search_title = text_p1.getText();
                    recycle();
                }
            }
        });
    }

    /**
     * 초기 화면에서 검색창에 영화 제목을 넣어 검색했을 때 Layout을 새롭게 배치합니다.
     */
    private void recycle() {
        CrawlingClient();
        /**
         * 크롤링한 값을 JFrame에 적용시킵니다.
         */
        String header[] = {"아이디", "내용", "평점", "날짜"};
        JTable review = new JTable(review_sum,header);
        JScrollPane reviewScroll = new JScrollPane(review);

        JLabel gradeAudience = new JLabel("관람객 ");
        JLabel audienceStar = new JLabel(score_adc);
        JLabel gradeCritic = new JLabel("평론가");
        JLabel criticStar = new JLabel(score_spec);
        JLabel gradeNetizen = new JLabel("네티즌");
        JLabel netizenStar = new JLabel(score_ntz);
        JLabel gradeMy = new JLabel("내 평점");
        JLabel myStar = new JLabel("0.0");
        JTextArea story = new JTextArea(summary);
        JScrollPane storyScroll = new JScrollPane(story);

        poster.removeAll();
        welcome.removeAll();

        movieposter.setBounds(5, 3, 295, 270);
        movieposter.setBorder(new LineBorder(Color.black));
        poster.add(movieposter);

        grade.setBounds(301, 3, 280, 90);
        grade.setBorder(new LineBorder(Color.black));
        grade.setLayout(new GridLayout(2,4));
        gradeAudience.setHorizontalAlignment(JLabel.LEFT);
        audienceStar.setFont(new Font("", Font.BOLD, 25));
        gradeCritic.setHorizontalAlignment(JLabel.LEFT);
        criticStar.setFont(new Font("", Font.BOLD, 25));
        gradeNetizen.setHorizontalAlignment(JLabel.LEFT);
        netizenStar.setFont(new Font("", Font.BOLD, 25));
        gradeMy.setHorizontalAlignment(JLabel.LEFT);
        myStar.setFont(new Font("", Font.BOLD, 25));
        grade.add(gradeAudience);
        grade.add(audienceStar);
        grade.add(gradeCritic);
        grade.add(criticStar);
        grade.add(gradeNetizen);
        grade.add(netizenStar);
        grade.add(gradeMy);
        grade.add(myStar);
        poster.add(grade);


        story.setLineWrap(true);
        story.setEditable(false);
        storyScroll.setBounds(301, 93, 280, 180);
        storyScroll.setBorder(new LineBorder(Color.black));
        poster.add(storyScroll);

        review.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        review.getColumn("아이디").setPreferredWidth(60);
        review.getColumn("내용").setPreferredWidth(400);
        review.getColumn("평점").setPreferredWidth(50);
        review.getColumn("날짜").setPreferredWidth(80);
        reviewScroll.setBounds(5, 275, 576, 222);
        reviewScroll.setBorder(new LineBorder(Color.black));
        poster.add(reviewScroll);



        welcome.setLayout(null);
        reviewText.setBounds(5, 4, 400, 105);
        score10.setBounds(510, 4, 75, 55);
        score10.setFont(new Font("", Font.BOLD, 30));
        score10.setHorizontalAlignment(JLabel.CENTER);
        score.setFont(new Font("", Font.BOLD, 30));
        score.setHorizontalAlignment(JTextField.CENTER);
        score.setBounds(410, 4, 100, 55);
        score.setBorder(new LineBorder(Color.black));
        registration.setBounds(410, 61, 175, 50);
        welcome.add(score);
        welcome.add(score10);
        welcome.add(registration);
        welcome.add(reviewText);

        add(poster);
        add(welcome);

        setVisible(false);
        setVisible(true);
    }
}
