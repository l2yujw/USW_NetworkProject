package Test;

public class Test {
    public static void main(String[] args) {
//        WebCrawlingTest webCrawlingTest = new WebCrawlingTest("어벤져스");
        Thread thread = new Thread(new WebCrawlingTest("어벤져스"));
        thread.start();
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(WebCrawlingTest.movie_title);
        System.out.println(WebCrawlingTest.poster);

    }
}
