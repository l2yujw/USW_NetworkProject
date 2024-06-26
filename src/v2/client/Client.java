package v2.client;

import v2.dto.MovieDto;
import v2.dto.MovieSearchDto;
import v2.server.ChatServer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ChatServer, CrawlingServer 가동 후 실행
 */
public class Client extends JFrame {

    public static List<String> mainTitle = new ArrayList<>();
    public static List<String> mainPoster = new ArrayList<>();
    public static String searchTitle;
    public static String movieScore;
    public static String genre;

    public static String summary;
    public static String posterUrl;
    public static String[][] review_sum;

    /**
     * 필드값에 대한 설명입니다. 대부분 이름의 뜻과 같습니다.
     */
    private static String userID;
    private JPanel search;
    private JLabel labelSearch;
    private JTextField searchMovie;
    private JButton btnSearch;
    private JPanel poster;
    private JLabel[] labelPoster;
    private JLabel[] labelTitle;
    private JPanel welcome;
    private JLabel labelUser;

    private final JLabel moviePoster = new JLabel();
    private final JLabel grade = new JLabel();
    private JComboBox<String> score;
    private final JLabel score10 = new JLabel("/ 10");
    private final JButton registration = new JButton("등록");
    private final JTextArea reviewText = new JTextArea();



    private final static String UNSECURED_CHAR_REGULAR_EXPRESSION =
            "select|delete|update|insert|create|alter|drop";
    private static Pattern unsecuredCharPattern;

    public static void initialize(){
        unsecuredCharPattern = Pattern.compile(UNSECURED_CHAR_REGULAR_EXPRESSION,Pattern.CASE_INSENSITIVE);
    }
    private static String makeSecureString(final String str, int maxLength)
    {
        String secureStr = str.substring(0, maxLength);
        Matcher matcher = unsecuredCharPattern.matcher(secureStr);
        if(matcher.find()){
            return matcher.replaceAll("");
        }
        else{
            return secureStr;
        }
    }

    public static String makeSecureString2(String str){
        String match = "[^\uAC00-\uD7A30-9a-zA-Z\\s]";
        str = str.replaceAll(match, "");
        return str;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        checkID();
        new Client();
        ChatClient client = new ChatClient(userID);
        client.chatStart();
    }

    /**
     * JFrame 생성합니다.
     */
    public Client() throws IOException {
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
        overlap = ChatServer.overlapCheck(userID);
        while (overlap) {
            userID = JOptionPane.showInputDialog("이미 사용중인 아아디입니다. 다른 아이디를 입력해주세요!");
            overlap = ChatServer.overlapCheck(userID);
        }
    }

    /**
     * 초기 화면과 검색창에 영화 제목을 넣어 검색했을 때의 Layout을 구성합니다.
     */
    private void frameView() throws MalformedURLException {
        // 검색창 panel1
        getMovieInf();

        search = new JPanel();
        poster = new JPanel();
        poster.setLayout(null);
        welcome = new JPanel();

        labelSearch = new JLabel("검색창");
        searchMovie = new JTextField(38);
        btnSearch = new JButton("검색");
        search.add(labelSearch);
        search.add(searchMovie);
        search.add(btnSearch);
        search.setBounds(0,0,600,50);
        search.setBorder(new LineBorder(Color.black));

        // 1 ~ 6순위 포스터
        labelPoster = new JLabel[6]; //포스터 사진
        labelTitle = new JLabel[6]; //[순위]영화 제목
        for(int i = 0; i<6; i++) {
            labelPoster[i] = new JLabel();
            labelTitle[i] = new JLabel();

            if (i == 0) {
                labelPoster[i].setBounds(45, 55, 160, 200);
                ImageIcon icon = imageSize(mainPoster.get(0));
                labelPoster[i].setIcon(icon);
                labelTitle[i].setBounds(45, 40, 160, 15);
                labelTitle[i].setText("1순위 : " + mainTitle.get(0));
            }
            if (i == 1) {
                labelPoster[i].setBounds(215, 55, 160, 200);
                ImageIcon icon = imageSize(mainPoster.get(1));
                labelPoster[i].setIcon(icon);
                labelTitle[i].setBounds(215, 40, 160, 15);
                labelTitle[i].setText("2순위 : " + mainTitle.get(1));
            }
            if (i == 2) {
                labelPoster[i].setBounds(385, 55, 160, 200);
                ImageIcon icon = imageSize(mainPoster.get(2));
                labelPoster[i].setIcon(icon);
                labelTitle[i].setBounds(385, 40, 160, 15);
                labelTitle[i].setText("3순위 : " + mainTitle.get(2));
            }
            if (i == 3) {
                labelPoster[i].setBounds(45, 280, 160, 200);
                ImageIcon icon = imageSize(mainPoster.get(3));
                labelPoster[i].setIcon(icon);
                labelTitle[i].setBounds(45, 265, 160, 15);
                labelTitle[i].setText("4순위 : " + mainTitle.get(3));
            }
            if (i == 4) {
                labelPoster[i].setBounds(215, 280, 160, 200);
                ImageIcon icon = imageSize(mainPoster.get(4));
                labelPoster[i].setIcon(icon);
                labelTitle[i].setBounds(215, 265, 160, 15);
                labelTitle[i].setText("5순위 : " + mainTitle.get(4));
            }
            if (i == 5) {
                labelPoster[i].setBounds(385, 280, 160, 200);
                ImageIcon icon = imageSize(mainPoster.get(5));
                labelPoster[i].setIcon(icon);
                labelTitle[i].setBounds(385, 265, 160, 15);
                labelTitle[i].setText("6순위 : " + mainTitle.get(5));
            }
            labelPoster[i].setBorder(new LineBorder(Color.black));

            poster.add(labelPoster[i]);
            poster.add(labelTitle[i]);

        }

        poster.setBounds(0, 50, 600, 500);
        poster.setBorder(new LineBorder(Color.black));

        // 환영문구
        labelUser = new JLabel(userID + "님 방문을 환영합니다.");
        labelUser.setFont(new Font("", Font.BOLD, 30));
        labelUser.setHorizontalAlignment(JLabel.CENTER);
        welcome.add(labelUser);
        welcome.setBounds(0, 550, 600, 150);
        welcome.setBorder(new LineBorder(Color.black));

        add(search);
        add(poster);
        add(welcome);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchMovie.getText().equals("")) {
                    JOptionPane.showMessageDialog(getContentPane(), "검색어를 입력해주세요!");
                }
                else {
                    searchTitle = searchMovie.getText();
                    searchTitle =makeSecureString(searchTitle,searchTitle.length());//명령어 형식 제거
                    searchTitle = makeSecureString2(searchTitle);//특수문자 제거
                    try {
                        searchView();
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }

    /**
     * 초기 화면에서 검색창에 영화 제목을 넣어 검색했을 때 Layout을 새롭게 배치합니다.
     */
    private void searchView() throws MalformedURLException {
        getMovieSearch(); //검색 정보 요청

        String[] scoreBox = {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
        score = new JComboBox<>(scoreBox);

        /**
         * 크롤링한 값을 JFrame에 적용시킵니다.
         */
        String[] header = {"아이디", "내용", "평점", "날짜"};

        DefaultTableModel model = new DefaultTableModel(review_sum,header);
        JTable review = new JTable(model);
        JScrollPane reviewScroll = new JScrollPane(review);

        JLabel genreName = new JLabel("장르");
        JLabel genreCheck = new JLabel(genre);
        JLabel emptyName = new JLabel(" ");
        JLabel empty = new JLabel(" ");
        JLabel gradeName = new JLabel("평점");
        JLabel gradeScore = new JLabel(movieScore);
        JLabel gradeMy = new JLabel("내 평점");
        JLabel myStar = new JLabel("0.00");
        JTextArea story = new JTextArea(summary);
        JScrollPane storyScroll = new JScrollPane(story);

        poster.removeAll();
        welcome.removeAll();
        grade.removeAll();

        moviePoster.setBounds(5, 3, 295, 270);
        moviePoster.setBorder(new LineBorder(Color.black));
        ImageIcon icon = imageSizeSearch(posterUrl);
        moviePoster.setIcon(icon);
        poster.add(moviePoster);

        grade.setBounds(301, 3, 280, 90);
        grade.setBorder(new LineBorder(Color.black));
        grade.setLayout(new GridLayout(2,4));
        genreName.setHorizontalAlignment(JLabel.LEFT);
        genreCheck.setFont(new Font("", Font.BOLD, 10));
        genreCheck.setHorizontalAlignment(JLabel.LEFT);
        empty.setHorizontalAlignment(JLabel.LEFT);
        empty.setFont(new Font("", Font.BOLD, 25));
        gradeName.setHorizontalAlignment(JLabel.LEFT);
        gradeScore.setFont(new Font("", Font.BOLD, 25));
        gradeMy.setHorizontalAlignment(JLabel.LEFT);
        myStar.setFont(new Font("", Font.BOLD, 25));
        grade.add(genreName);
        grade.add(genreCheck);
        grade.add(emptyName);
        grade.add(empty);
        grade.add(gradeName);
        grade.add(gradeScore);
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
        score.setFont(new Font("", Font.BOLD, 15));
        score.setBounds(410, 4, 100, 55);
        score.setBorder(new LineBorder(Color.black));
        registration.setBounds(410, 61, 175, 50);
        welcome.add(score);
        welcome.add(score10);
        welcome.add(registration);
        welcome.add(reviewText);


        //리뷰를 등록합니다
        registration.addActionListener(e -> {
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.dd.MM HH:mm");
            String review_date = formatter.format(date);
            String inputStr[] = new String[4];

            inputStr[0] = userID;
            inputStr[1] = reviewText.getText();
            inputStr[2] = score.getSelectedItem().toString();
            inputStr[3] = review_date;

            for(int i=0; i < review.getRowCount(); i++){
                if(review.getValueAt(i,0) == userID){
                    model.removeRow(i);//같은 아이디로 이미 작성했으면 제거
                    break;
                }
            }

            model.addRow(inputStr);

            if (inputStr[2].equals("10")) {
                myStar.setText(inputStr[2] + ".0");
            }else{
                myStar.setText(inputStr[2] + ".00");
            }//내 점수 표시

            reviewText.setText("");
        });
        add(poster);
        add(welcome);

        setVisible(false);
        setVisible(true);
    }

    /**
     * CrawlingServer 초기화면 관련한 데이터를 dto로 받아옵니다
     */
    public void getMovieInf(){
        try {
            Socket socket = new Socket("localhost", 5000);

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);

            oos.writeObject("first");

            List<MovieDto> movieDtoList = (List<MovieDto>) ois.readObject();

            for (MovieDto movieDto : movieDtoList) {
                mainTitle.add(movieDto.getTitle());
                mainPoster.add(movieDto.getPosterUrl());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * CrawlingServer로부터 검색 결과값을 dto로 받아옵니다.
     */
    public void getMovieSearch() {
        try {
            Socket socket = new Socket("localhost", 5000);

            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            ObjectOutputStream oos = new ObjectOutputStream(os);
            ObjectInputStream ois = new ObjectInputStream(is);

            oos.writeObject(searchTitle);

            MovieSearchDto movieSearchDto = (MovieSearchDto) ois.readObject();
            movieScore = movieSearchDto.getScore();
            genre = movieSearchDto.getGenre();
            summary = movieSearchDto.getSummary();
            posterUrl = movieSearchDto.getPosterUrl();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 초기화면과 검색화면의 포스터 이미지 크기를 설정합니다.
     */
    private ImageIcon imageSize(String posterUrl) throws MalformedURLException {
        ImageIcon icon = new ImageIcon(new URL(posterUrl));
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(160, 200, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImg);
    }
    private ImageIcon imageSizeSearch(String posterUrl) throws MalformedURLException {
        ImageIcon icon = new ImageIcon(new URL(posterUrl));
        Image img = icon.getImage();
        Image changeImg = img.getScaledInstance(295, 270, Image.SCALE_SMOOTH);
        return new ImageIcon(changeImg);
    }
}
