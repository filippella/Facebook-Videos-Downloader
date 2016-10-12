/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookvideosdownloader;

import java.io.*;
import java.net.*;

/**
 *
 * @author Filippo
 */
public class DownloaderService implements Runnable {

    private static final int TIMEOUT = 5000;
    private static final int BLOCK_SIZE = 1024;
    private final String url, fileName;

    public DownloaderService(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setReadTimeout(TIMEOUT);
            connection.connect();

            //String fileName = new java.io.File(url).getName();
//          
//            File fileToSave = new File(fileName);        
            FileOutputStream out = new FileOutputStream(new File("C:\\mezmur\\"+fileName));

            int fileSize = connection.getContentLength();

            System.out.println("File Size = " + fileSize);

            double total = 0.0;

            InputStream input = connection.getInputStream();

            byte buffer[] = new byte[BLOCK_SIZE];
            int lenght = -1;

            while ((lenght = input.read(buffer, 0, BLOCK_SIZE)) != -1) {
                total = total + lenght;
                out.write(buffer, 0, lenght);
                //this.listener.onDownloadProgress("Downloaded so far " + lenght, (int) ((total / fileSize) * 100));
            }

            input.close();
            out.close();
            //this.listener.onDownloadComplete();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error -> " + e.getMessage());
            //this.listener.onDownloadFailed(e.getMessage());
        }
    }
}
