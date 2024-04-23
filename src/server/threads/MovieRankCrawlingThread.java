package server.threads;

import server.MovieRankCrawling;
import server.dto.MovieRankDto;

import java.io.*;
import java.net.Socket;

public class MovieRankCrawlingThread extends Thread{

    ObjectInputStream ois;
    ObjectOutputStream oos;
    OutputStream os;
    InputStream is;
    public MovieRankCrawlingThread(Socket socket){
        is = null;
        try {
            is = socket.getInputStream();
            os = socket.getOutputStream();
            ois = new ObjectInputStream(is);
            oos = new ObjectOutputStream(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        Thread thread = new Thread(new MovieRankCrawling());
        thread.start();
        try {
            thread.join();
            MovieRankDto movieRankObj = new MovieRankDto();
//            movieRankObj.setMain_title(MovieRankCrawling.main_title);
//            movieRankObj.setMain_poster(MovieRankCrawling.main_poster);
//            movieRankObj.setMain_sum(MovieRankCrawling.main_sum);


            oos.writeObject(movieRankObj);//영화 랭킹 정보를 제공
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
