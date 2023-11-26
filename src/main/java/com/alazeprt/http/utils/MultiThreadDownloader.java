package com.alazeprt.http.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiThreadDownloader {
    public static volatile int progress = -1;
    public static volatile int maxProgress = -1;
    public static void downloadFile(String strurl, String downloadPath, int threadCount, Thread willStartThread) {
        try {
            URL url = new URL(strurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int fileSize = connection.getContentLength();
            maxProgress = fileSize;
            progress = 0;
            willStartThread.start();

            int blockSize = fileSize / threadCount;
            int lastBlockSize = blockSize + fileSize % threadCount;

            Thread[] threads = new Thread[threadCount];

            for (int i = 0; i < threadCount; i++) {
                int startByte = i * blockSize;
                int endByte = (i == threadCount - 1) ? startByte + lastBlockSize - 1 : startByte + blockSize - 1;

                threads[i] = new DownloadThread(strurl, downloadPath, startByte, endByte);
                threads[i].start();
            }

            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class DownloadThread extends Thread {
        private final String strurl;
        private final String downloadPath;
        private final int startByte;
        private final int endByte;

        public DownloadThread(String strurl, String downloadPath, int startByte, int endByte) {
            this.strurl = strurl;
            this.downloadPath = downloadPath;
            this.startByte = startByte;
            this.endByte = endByte;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(strurl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);

                try (InputStream inputStream = connection.getInputStream();
                     RandomAccessFile outputStream = new RandomAccessFile(downloadPath, "rw")) {

                    outputStream.seek(startByte);

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        synchronized (MultiThreadDownloader.class) {
                            progress+=bytesRead;
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}