# Network Programming (Movie Review)

<br>

# 💭 Idea
![img.png](image_readme/idea.png)


# 🎸Result

|  |  |
| --- | --- |
| <img width="600" src="https://github.com/l2yujw/USW_NetworkProject/assets/49338509/173d0fb8-6f1e-4de5-82d5-f292811675d2"> | <img width="600" src="https://github.com/l2yujw/USW_NetworkProject/assets/49338509/378c46d6-718f-485f-a15e-e779668337ec"> |
| <img width="600" src="https://github.com/l2yujw/USW_NetworkProject/assets/49338509/4746ee1f-d53e-449c-b6c5-fa2766545696"> | <img width="600" src="https://github.com/l2yujw/USW_NetworkProject/assets/49338509/8d717a9e-684b-4dc6-a74e-8512cea478d1"> |

<br>

---

<br><br>

# ✅ Architecture & Stack

Language : Java

UI : Jframe

Server & Client : JAVA / Socket Programming

<br><br>

# ✅ WebCrawling : 
  - 네이버 영화 서비스 종료
  - 크롤링 무비차트로 변경
  ```java
    String url = "https://www.moviechart.co.kr/rank/realtime/index/image";// 영화 랭킹
        Connection conn = Jsoup.connect(url);

        Document doc = conn.get();
        Elements movieList = doc.getElementsByClass("movieBox-item");

        ArrayList<MovieDto> movieDtoList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            movieDtoList.add(
                    new MovieDto(
                            movieList.get(i).select("a img").attr("alt"),
                            movieList.get(i).select("a img").attr("src").substring(51)
                    )
            );
        }
  ```

<br><br>

# ✅ Chat

  - 유저 등록
  ```java
    PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(userID);
                out.flush();
                while (true) {
                    sendBtn.addActionListener(e -> {
                        if(!fieldMsg.getText().equals("")) {
                            String outputMsg = fieldMsg.getText();
                            out.println(outputMsg);
                            out.flush();
                            fieldMsg.setText("");
                        }
                    });
                }
  ```



<br><br>

# ✅ Server & Client

  ✅ ChatServer
  ```java
  while (true) {
        System.out.println("Waiting v2.Client...");
        socket = serverSocket.accept();

        // Client가 접속할 때마다 새로운 Thread를 생성한다.
        ReceiveClientThread receiveClientThread = new ReceiveClientThread(socket);
        receiveClientThread.start();
  }
  ```

  ✅ CrawlingServer
  ```java
  while (true) {
      System.out.println("Waiting v2.Client...");
      socket = serverSocket.accept();

      CrawlingThread crawlingThread = new CrawlingThread(socket);
      crawlingThread.start();
  }
  ```

✅ Client
- 초기화면 크롤링 데이터
```java
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
```
  - 검색 크롤링 데이터
```java
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
```
