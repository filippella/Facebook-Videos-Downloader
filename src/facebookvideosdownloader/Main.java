/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookvideosdownloader;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Filippo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String sCurrentLine;
        BufferedReader br = null;

        String json = "";

        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Filippo\\Desktop\\mezmur_with_video.json"));
            StringBuffer buffer = new StringBuffer();
            while ((sCurrentLine = br.readLine()) != null) {
                buffer.append(sCurrentLine);
                System.out.println(sCurrentLine);
            }
            json = buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        MezmurObject mo = new Gson().fromJson(json, MezmurObject.class);
        Data[] data = mo.getData();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        int count = 0;
        
        for (Data d : data) {

            String description = d.getDescription();
            String message = d.getMessage();

            if (message != null) {
                String title = message.trim().split(" ")[0].replace("\\", "");
                String source = d.getSource();
                if (title != null && source != null) {
                    executor.submit(new DownloaderService(source, count + title + ".mp4"));
                }
            } else if (description != null) {
                String title = description.trim().split(" ")[0];
                String source = d.getSource();
                if (title != null && source != null) {
                    executor.submit(new DownloaderService(source, count + title + ".mp4"));
                }
            }
            count++;
        }

        executor.shutdown();

//        List<String> urls = new ArrayList<>();
//        urls.add("https://video.xx.fbcdn.net/v/t42.1790-2/14172780_172035853221616_662588439_n.mp4?efg=eyJybHIiOjMwMCwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoidjJfNDAwX2NyZl8yN19tYWluXzMuMF9zZCJ9&rl=300&vabr=56&oh=4050e1b61c053e06c995132af7482411&oe=57C9CD13");
//        urls.add("https://video.xx.fbcdn.net/v/t42.1790-2/14203874_1847171185514881_1161549264_n.mp4?efg=eyJybHIiOjMwMCwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoidjJfNDAwX2NyZl8yN19tYWluXzMuMF9zZCJ9&rl=300&vabr=55&oh=10bdb79b4156bcc6f998b6a21cd450f5&oe=57CA1097");
//        urls.add("https://video.xx.fbcdn.net/v/t42.1790-2/14195860_691487327671849_1072978756_n.mp4?efg=eyJybHIiOjMwMCwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoidjJfNDAwX2NyZl8yN19tYWluXzMuMF9zZCJ9&rl=300&vabr=55&oh=07db4134e84f2b6102bd653fa5ff9fb8&oe=57C9D92E");
//        urls.add("https://video.xx.fbcdn.net/v/t42.1790-2/14195625_1089952421097501_1779943490_n.mp4?efg=eyJybHIiOjMwMCwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoidjJfNDAwX2NyZl8yN19tYWluXzMuMF9zZCJ9&rl=300&vabr=54&oh=44ee339b1f5c192c8828fdcca2cdae21&oe=57C9FD51");
//        urls.add("https://video.xx.fbcdn.net/v/t42.1790-2/14193251_1100046540084744_1962192441_n.mp4?efg=eyJybHIiOjMwMCwicmxhIjo1MTIsInZlbmNvZGVfdGFnIjoidjJfNDAwX2NyZl8yN19tYWluXzMuMF9zZCJ9&rl=300&vabr=54&oh=a50607ae876b937f1d1a30eb91387eeb&oe=57C9E7E0");
//        
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 6, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
//        
//        for(int i = 0; i < urls.size(); i++) {
//            executor.submit(new DownloaderService(urls.get(i), "mezmur_" + i + ".mp4"));
//        }
//        executor.shutdown();
    }
}
